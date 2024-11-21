package band.effective.office.tv.network.mattermost.mattermostWebSocketClient.impl

import android.util.Log
import band.effective.office.tv.BuildConfig
import band.effective.office.tv.core.network.Either
import band.effective.office.tv.domain.botLogic.BotConfig
import band.effective.office.tv.domain.botLogic.model.BotEvent
import band.effective.office.tv.domain.botLogic.model.BotInfo
import band.effective.office.tv.domain.botLogic.model.toBotInfo
import band.effective.office.tv.domain.model.message.BotMessage
import band.effective.office.tv.domain.model.message.toBotEvent
import band.effective.office.tv.domain.model.message.toBotMessage
import band.effective.office.tv.network.mattermost.MattermostApi
import band.effective.office.tv.network.mattermost.mattermostWebSocketClient.MattermostWebSocketClient
import band.effective.office.tv.network.mattermost.model.AuthJson
import band.effective.office.tv.network.mattermost.model.OtherJson
import band.effective.office.tv.network.mattermost.model.Post
import band.effective.office.tv.network.mattermost.model.PostJson
import band.effective.office.tv.network.mattermost.model.PostMessageData
import band.effective.office.tv.network.mattermost.model.ReactionJson
import band.effective.office.tv.network.mattermost.model.WebSocketAuthJson
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject


class MattermostWebSocketClientImpl @Inject constructor(
    private val client: OkHttpClient, private val api: MattermostApi, private val moshi: Moshi
) : MattermostWebSocketClient {
    private val webSocket by lazy {
        client.newWebSocket(Request.Builder().url("ws://${BuildConfig.apiMattermostUrl}websocket")
            .build(),
            MattermostWebSocketListener { handler(it) })
    }
    private var lastSeq = 0
    lateinit var authResponse: AuthJson
    lateinit var eventHandler: (BotEvent) -> Unit

    private class MattermostWebSocketListener(private val handler: (String) -> Unit) :
        WebSocketListener() {
        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            handler(text)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Log.e("socket", t.toString())
        }
    }

    override suspend fun connect(): Boolean = when (val response = api.auth()) {
        is Either.Success -> {
            authResponse = response.data
            webSocket.send(
                moshi.adapter(WebSocketAuthJson::class.java).toJson(WebSocketAuthJson.default)
            )
            true
        }

        is Either.Failure -> {
            Log.e("socket", response.error.message)
            false
        }
    }


    private fun handler(response: String) {
        when {
            response.isPost() -> {
                val post =
                    moshi.adapter(PostJson::class.java).fromJson(response.correct())?.toBotEvent()
                Log.i("socket", post.toString())
                if (post != null && !post.botIsAuthor()) {
                    eventHandler(post)
                }
            }
            response.isReaction() -> {
                val reaction = moshi.adapter(ReactionJson::class.java).fromJson(response.correct())
                    ?.toBotEvent()
                Log.i("socket", reaction.toString())
                if (reaction != null && !reaction.botIsAuthor()) {
                    eventHandler(reaction)
                }
            }
            else -> {
                Log.i("socket", response)
                lastSeq = moshi.adapter(OtherJson::class.java).fromJson(response.correct())?.seq
                    ?: lastSeq
            }
        }
    }


    override suspend fun postMessage(channelId: String, message: String, root: String) {
        api.createPost(PostMessageData(channelId, message, root))
    }

    override suspend fun saveMessage(message: BotMessage): BotMessage {
        val post = api.createPost(
            PostMessageData(
                channelId = BotConfig.directId, message = "save", rootId = "", props = message
            )
        )
        return message.copy(
            directId = when (post) {
                is Either.Success -> post.data.id
                is Either.Failure -> {
                    Log.e("socket", post.error.message)
                    ""
                }
            }
        )
    }


    override fun botInfo(): BotInfo = authResponse.toBotInfo()
    override suspend fun getDirectMessages(): List<BotMessage> =
        when (val response = api.getChanelPosts(BotConfig.directId)) {
            is Either.Success -> response.data.order.mapNotNull {
                when (val postResponse = api.getPost(it)) {
                    is Either.Success -> postResponse.data.props.copy(directId = it)
                    is Either.Failure -> {
                        Log.e("socket", postResponse.error.message)
                        null
                    }
                }
            }
            is Either.Failure -> {
                Log.e("socket", response.error.message)
                listOf()
            }
        }

    override suspend fun deleteMessage(messageId: String): Boolean =
        when (val response = api.deletePost(messageId)) {
            is Either.Success -> true
            is Either.Failure -> {
                Log.e("socket", response.error.message)
                false
            }
        }

    override suspend fun getUserName(userId: String): String =
        when (val response = api.getUser(userId)) {
            is Either.Success -> response.data.username
            is Either.Failure -> {
                Log.e("socket", response.error.message)
                "@john.doe"
            }
        }

    override suspend fun getMessage(messageId: String): BotMessage {
        val message = when (val response = api.getPost(messageId)) {
            is Either.Success -> response.data
            is Either.Failure -> {
                Log.e("socket", response.error.message)
                Post.errorPost.copy(message = response.error.message)
            }
        }
        val author = getUserName(message.userId)
        return message.toBotMessage(author)
    }


    override fun disconnect() {
        webSocket.close(1000, null)
    }


    override fun subscribe(handler: (BotEvent) -> Unit) {
        eventHandler = handler
    }

    private fun String.isPost(): Boolean = contains("posted")

    private fun String.isReaction(): Boolean = contains("reaction_added")

    private fun String.correct(): String =
        replace("\\n", "\n").replace("\\", "").replace("\"{", "{").replace("}\"", "}")
            .replace("seq_reply", "seq").replace("\"[", "[").replace("]\"", "]")

    private fun BotEvent.botIsAuthor() = userId == authResponse.id
}


