package band.effective.office.elevator.data.repository

import band.effective.office.elevator.domain.repository.UserRepository
import band.effective.office.elevator.domain.models.User

class UserRepositoryImpl : UserRepository {

    override suspend fun getLastUserId(): String {
        return ""
    }
}