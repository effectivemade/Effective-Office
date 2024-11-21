package band.effective.office.elevator.domain.repository

interface UserRepository {
    suspend fun getLastUserId():String
}