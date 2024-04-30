package band.effective.mattermost

import band.effective.MattermostSettings
import band.effective.core.Either
import band.effective.core.ErrorReason
import band.effective.core.mattermostApi
import band.effective.mattermost.models.FileInfo
import band.effective.mattermost.models.UserInfo
import band.effective.mattermost.models.response.models.EmojiInfo
import band.effective.mattermost.models.response.models.EmojiInfoForApi
import band.effective.mattermost.models.response.models.Post
import band.effective.mattermost.models.response.models.toDataModel
import band.effective.mattermost.models.response.toUserInfo
import band.effective.utils.getEnv
import band.effective.utils.toInputStream
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*
import javax.imageio.ImageIO
import javax.imageio.stream.ImageInputStream

class MattermostRepositoryImpl(private val token: String, private val coroutineScope: CoroutineScope) :
    MattermostRepository {

    private var userId: String? = null

    init {
        initUser()
    }

    private fun initUser() {
        coroutineScope.launch {
            when (val userInfo = getUserIdFromToken()) {
                is Either.Success -> {
                    userId = userInfo.data.userId
                }

                is Either.Failure -> {
                    throw Error("Token is not correct or user not found. \n Token should be like this: Bearer <token from mattermost>")
                }
            }
        }
    }

    override suspend fun downloadFile(fileId: String): ByteArray? {
        val response: Response<ResponseBody> = mattermostApi.downloadFile(token = token, fileId = fileId)

        val mediaType: MediaType? = response.body()?.contentType()
        val type: String? = mediaType?.type()

        val buffer: ByteArray? = response.body()?.byteStream()?.readBytes()
        println("read byte from mattermost ${buffer?.size}")

        val transcodeToPNG: ByteArray? = transcodeToPNG(imageData = buffer, type = type)
        println("transcoded image bytes ${transcodeToPNG?.size}")

        response.body()?.byteStream()?.close()
        return transcodeToPNG
    }

    private suspend fun getAllPostsFromChannels(): Either<ErrorReason, List<Post>> {
        when (val channels = mattermostApi.getChannelMattermost(token)) {
            is Either.Success -> {
                val postsCache = Collections.synchronizedList(mutableListOf<Post>())
                channels.data.forEach { channel ->
                    withContext(Dispatchers.IO) {
                        val posts = mattermostApi.getPostsFromChannel(
                            token = token,
                            channelId = channel.id,
                            sinceTime = System.currentTimeMillis() - MILLISECOND_IN_DAY
                        )
                        when (posts) {
                            is Either.Failure -> {
                                println(posts.error.message)
                            }

                            is Either.Success -> {
                                postsCache.addAll(posts.data.posts.postsData)
                            }
                        }
                    }
                }
                return Either.Success(postsCache.toList())
            }

            is Either.Failure -> {
                return channels
            }
        }
    }

    /*
    This method return fileIds from posts, what has "star" reaction without "save"
    * */
    override suspend fun getFilesIdsFromPosts(): Either<ErrorReason, List<FileInfo>> =
        when (val posts = getAllPostsFromChannels()) {
            is Either.Success -> {
                val postsWithReaction = posts.data.filter { post ->
                    if (post.metadata.reactions != null) {
                        val countToRequestSaveReaction =
                            post.metadata.reactions.count { reaction -> reaction.emoji_name == getEnv(MattermostSettings.emojiToRequestSave) }
                        val countSaveReaction =
                            post.metadata.reactions.count { reaction -> reaction.emoji_name == getEnv(MattermostSettings.emojiToSaveSuccess) }
                        countToRequestSaveReaction > 0 && countSaveReaction == 0
                    } else false
                }
                val filesInPostsWithReaction = postsWithReaction.map { post -> post.metadata.files }

                val files: List<FileInfo> = filesInPostsWithReaction.flatMap { listIsFile ->
                    listIsFile?.filter { file ->
                        file.mime_type.contains("image")
                    }?.map { file ->
                        FileInfo(file.id, file.name, file.mime_type, file.post_id)
                    } ?: emptyList()
                }
                Either.Success(files)
            }

            is Either.Failure -> {
                posts
            }
        }

    override suspend fun makeReaction(emojiInfo: EmojiInfo): Either<ErrorReason, EmojiInfoForApi> =
        mattermostApi.makeReaction(
            token = token,
            emojiInfo = emojiInfo.toDataModel(userId = userId.orEmpty())
        )

    private suspend fun getUserIdFromToken(): Either<ErrorReason, UserInfo> =
        when (val userInfo = mattermostApi.getUserInfoFromToken(token)) {
            is Either.Success -> {
                Either.Success(userInfo.data.toUserInfo())
            }

            is Either.Failure -> {
                Either.Failure(userInfo.error)
            }
        }

    private fun transcodeToPNG(imageData: ByteArray?, type: String?): ByteArray? {
        if (imageData == null) {
            return imageData
        }
        if (type?.startsWith("image/") == true) {
            val fileExtension = "png" //Transcode to this photo extension
            try {
                val stream: ImageInputStream = ImageIO.createImageInputStream(imageData.toInputStream())
                val inputImage: BufferedImage = ImageIO.read(stream)
                val outputStream = ByteArrayOutputStream()
                val result: Boolean = ImageIO.write(inputImage, fileExtension, outputStream)

                //close all streams
                stream.close()
                inputImage.flush()

                return if (result) outputStream.toByteArray() else imageData
            } catch (e: IOException) {
                e.printStackTrace()
                println("Error in transcoding image: ${e.message}")
                return imageData
            }
        } else {
            return imageData
        }
    }
}

private const val MILLISECOND_IN_DAY = 86400000