package band.effective.office.tv.repository.uselessFactRepository

interface UselessFactRepository {
    suspend fun fact(): String
}