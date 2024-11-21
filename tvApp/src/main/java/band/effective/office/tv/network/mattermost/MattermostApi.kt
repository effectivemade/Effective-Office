package band.effective.office.tv.network.mattermost

import band.effective.office.tv.core.network.Either
import band.effective.office.tv.core.network.ErrorReason
import band.effective.office.tv.network.mattermost.model.*
import retrofit2.http.*

interface MattermostApi {
    @GET("users/me")
    suspend fun auth(): Either<ErrorReason, AuthJson>

    @POST("posts")
    suspend fun createPost(@Body post: PostMessageData): Either<ErrorReason, Post>

    @GET("channels/{channel_id}/posts")
    suspend fun getChanelPosts(@Path("channel_id") channelId: String): Either<ErrorReason, GetPostsJson>

    @GET("posts/{post_id}")
    suspend fun getPost(@Path("post_id") postId: String): Either<ErrorReason, Post>

    @DELETE("posts/{post_id}")
    suspend fun deletePost(@Path("post_id") postId: String): Either<ErrorReason, DeleteResponse>

    @GET("users/{user_id}")
    suspend fun getUser(@Path("user_id") userId: String): Either<ErrorReason, GetUserJson>
}