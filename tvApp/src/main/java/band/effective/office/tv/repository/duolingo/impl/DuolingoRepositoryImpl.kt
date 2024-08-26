package band.effective.office.tv.repository.duolingo.impl

import band.effective.office.tv.core.network.Either
import band.effective.office.tv.domain.model.duolingo.DuolingoUser
import band.effective.office.tv.domain.model.duolingo.toDomain
import band.effective.office.tv.domain.model.notion.EmploymentType
import band.effective.office.tv.network.duolingo.DuolingoApi
import band.effective.office.tv.repository.duolingo.DuolingoRepository
import band.effective.office.tv.repository.workTogether.WorkTogether
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DuolingoRepositoryImpl @Inject constructor(
    private val duolingoApi: DuolingoApi,
    private val workTogether: WorkTogether
) : DuolingoRepository {
    override suspend fun getUsers(): Flow<Either<String, List<DuolingoUser>>> =
        flow {
            val users = workTogether.getAll().filter {
                it.duolingo != null
                        && it.employment == EmploymentType.Band.value
                        && it.status == "Active"
            }
            var error = false
            val data = users.mapNotNull {
                when (val response = duolingoApi.getUserInfo(it.duolingo!!)) {
                    is Either.Failure -> {
                        emit(Either.Failure(response.error.message))
                        error = true
                        null
                    }

                    is Either.Success -> response.data.toDomain()?.copy(username = it.name)
                }
            }
            if (!error) {
                emit(Either.Success(data))
            }
        }
}
