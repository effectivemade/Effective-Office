package band.effective

import band.effective.core.Either
import band.effective.mattermost.MattermostRepository
import band.effective.mattermost.models.FileInfo
import band.effective.mattermost.models.response.models.EmojiInfo
import band.effective.synology.SynologyRepository
import band.effective.utils.getEnv
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class BotManager(
    private val mattermost: MattermostRepository,
    private val synology: SynologyRepository,
    private val coroutineScope: CoroutineScope
) {
    suspend fun updatePhoto () {
        when (val filesIds = mattermost.getFilesIdsFromPosts()) {
            is Either.Success -> {
                filesIds.data.forEach { fileInfo ->
                    coroutineScope.launch(Dispatchers.IO) {
                        sendFileToSynology(fileInfo)
                    }
                }
            }
            is Either.Failure -> {
                println(filesIds.error.message)
            }
        }
    }

    private suspend fun sendFileToSynology(fileInfo: FileInfo) {
        mattermost.downloadFile(fileInfo.id)?.let { filesByte ->
            when (val uploadedFile = synology.uploadPhotoToAlbum(
                    file = filesByte,
                    fileName = fileInfo.fileName,
                    fileType = fileInfo.fileType
            )
            ) {
                is Either.Success -> {
                    if (uploadedFile.data.success) {
                        val emojiInfo = EmojiInfo(
                                createAt = System.currentTimeMillis(),
                                emojiName = getEnv(MattermostSettings.emojiToSaveSuccess),
                                postId = fileInfo.postId,
                        )
                        mattermost.makeReaction(emojiInfo)
                    }
                }
                is Either.Failure -> {
                    println(uploadedFile.error.message)
                }
            }
        }
    }
}
