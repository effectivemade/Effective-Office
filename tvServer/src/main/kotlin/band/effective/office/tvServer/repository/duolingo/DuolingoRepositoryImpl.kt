package band.effective.office.tvServer.repository.duolingo

import band.effective.office.tvServer.api.duolingo.DuolingoApi
import band.effective.office.tvServer.model.DuolingoUser
import band.effective.office.tvServer.model.toDomain
import band.effective.office.workTogether.Teammate
import band.effective.office.workTogether.WorkTogether

class DuolingoRepositoryImpl(
    private val api: DuolingoApi,
    private val workTogether: WorkTogether
) : DuolingoRepository {
    override suspend fun getUsers(): List<DuolingoUser> =
        workTogether.getActive()
            .filter { it.haveDuolingo() }
            .mapNotNull { api.getUser(it.duolingo!!).users.firstOrNull()?.toDomain()?.copy(username = it.name) }

    private fun Teammate.haveDuolingo(): Boolean = duolingo != null
}