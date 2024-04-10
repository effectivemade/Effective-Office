package band.effective.office.tvServer.repository.post

import band.effective.office.tvServer.model.MattermostMessage
import band.effective.office.tvServer.model.SavedMessage
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getPosts(): Flow<SavedMessage>
    suspend fun addPost(post: SavedMessage)
    suspend fun updatePost(post: SavedMessage)
    suspend fun deletePost(id: String)
}