package band.effective.office.tvServer.api.mattermost

import band.effective.office.tvServer.api.mattermost.model.*

interface MattermostApi {
    suspend fun getPost(postId: String): GetPostRespond
    suspend fun createPost(request: CreatePostRequest): CreatePostResponse
    suspend fun deletePost(postId: String): DeleteResponse
    suspend fun updatePost(postId: String, request: UpdateRequest): CreatePostResponse
    suspend fun getUser(userId: String): GetUserResponse
    suspend fun getPostsFromChannel(channelId: String): GetPostsFromChannelResponse
}