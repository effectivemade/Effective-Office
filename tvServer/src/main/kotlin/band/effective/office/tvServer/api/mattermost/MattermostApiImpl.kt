package band.effective.office.tvServer.api.mattermost

import band.effective.office.tvServer.api.mattermost.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class MattermostApiImpl(private val client: HttpClient) : MattermostApi {
    private val baseUrl = "https://mattermost.effective.band/api/v4"
    override suspend fun getPost(postId: String): GetPostRespond = client.get("$baseUrl/posts") {
        url {
            appendPathSegments(postId)
        }
    }.body()

    override suspend fun createPost(request: CreatePostRequest): CreatePostResponse = client.post("$baseUrl/posts") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body()

    override suspend fun deletePost(postId: String): DeleteResponse = client.delete("$baseUrl/posts") {
        url {
            appendPathSegments(postId)
        }
    }.body()

    override suspend fun updatePost(postId: String, request: UpdateRequest): CreatePostResponse =
        client.put("$baseUrl/posts") {
            url {
                appendPathSegments(postId)
            }
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    override suspend fun getUser(userId: String): GetUserResponse = client.get("$baseUrl/users") {
        url {
            appendPathSegments(userId)
        }
    }.body()

    override suspend fun getPostsFromChannel(channelId: String): GetPostsFromChannelResponse =
        client.get("$baseUrl/channels/$channelId/posts").body()
}