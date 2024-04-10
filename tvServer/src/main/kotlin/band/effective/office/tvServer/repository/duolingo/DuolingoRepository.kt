package band.effective.office.tvServer.repository.duolingo

import band.effective.office.tvServer.model.DuolingoUser

interface DuolingoRepository {
    suspend fun getUsers(): List<DuolingoUser>
}