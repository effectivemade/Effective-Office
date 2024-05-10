package band.effective.feedback.data.mattermost

import band.effective.feedback.data.mattermost.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class MattermostApi(private val client: HttpClient, private val baseUrl: String) {
    suspend fun me() = client.get("$baseUrl/api/v4/users/me").body<MattermosrUser>()
    suspend fun getUserInfo(userName: String) =
        client.get("$baseUrl/api/v4/users/username/$userName").body<MattermosrUser>()

    suspend fun getChanel(chanelName: String, teamName: String) =
        client.get("$baseUrl/api/v4/teams/name/$teamName/channels/name/$chanelName").body<MattermostChannel>()

    suspend fun createChannel(firstUserId: String, secondUserId: String) =
        client.post("$baseUrl/api/v4/channels/direct") {
            contentType(ContentType.Application.Json)
            setBody(listOf(firstUserId, secondUserId))
        }.body<MattermostChannel>()

    suspend fun writeMessage(channelId: String, message: String) = client.post("$baseUrl/api/v4/posts") {
        contentType(ContentType.Application.Json)
        setBody(Message(channelId, message))
    }.body<MattermostMessage>()

    suspend fun makeReaction(userId: String, postId: String, reactionName: String) =
        client.post("$baseUrl/api/v4/reactions") {
            contentType(ContentType.Application.Json)
            setBody(Reaction(userId, postId, reactionName))
        }
}