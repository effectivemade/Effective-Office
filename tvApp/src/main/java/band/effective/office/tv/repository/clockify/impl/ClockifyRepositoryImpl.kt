package band.effective.office.tv.repository.clockify.impl

import band.effective.office.tv.BuildConfig
import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.domain.model.clockify.ClockifyUser
import band.effective.office.tv.domain.model.clockify.toDomainList
import band.effective.office.tv.network.clockify.ClockifyApi
import band.effective.office.tv.network.clockify.models.request.ClockifyRequest
import band.effective.office.tv.network.clockify.models.request.DetailedFilter
import band.effective.office.tv.network.clockify.models.request.Projects
import band.effective.office.tv.repository.clockify.ClockifyRepository
import band.effective.office.tv.repository.workTogether.WorkTogether
import band.effective.office.tv.utils.DateUtlils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class ClockifyRepositoryImpl @Inject constructor(
    private val clockifyApi: ClockifyApi,
    private val workTogether: WorkTogether
) : ClockifyRepository {
    override suspend fun getTimeEntries(): Flow<Either<String, List<ClockifyUser>>> =
        flow {
            val users = workTogether.getAll().filter { it.employment in setOf("Band", "Intern") && it.status == "Active" }
            val rangeQuarter = DateUtlils.getDatesByCurrentQuarter()
            
            val clockifyRequestDTO = ClockifyRequest(
                amountShown = "HIDE_AMOUNT",
                dateRangeStart = rangeQuarter.first,
                dateRangeEnd = rangeQuarter.second,
                exportType = "JSON",
                rounding = false,
                detailedFilter = DetailedFilter(
                    sortColumn = "USER"
                ),
                projects = Projects(
                    ids = listOf(BuildConfig.clockifyProjectId)
                )
            )

            val response = clockifyApi.getAllTimeEntriesSport(
                workspaceId = BuildConfig.clockifyWorkspaceId,
                clockifyBody = clockifyRequestDTO
            )

            when(response) {
                is Either.Failure -> emit(Either.Failure(error = response.error.message))
                is Either.Success -> emit(Either.Success(response.data.toDomainList(users)))
            }
        }
}