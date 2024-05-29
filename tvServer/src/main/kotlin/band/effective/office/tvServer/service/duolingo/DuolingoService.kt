package band.effective.office.tvServer.service.duolingo

import band.effective.office.tvServer.repository.duolingo.DuolingoRepository

class DuolingoService(private val repository: DuolingoRepository) {
    suspend fun getUsers() = repository.getUsers()
}