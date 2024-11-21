package band.effective.office.elevator.data.repository

import band.effective.office.elevator.domain.models.ErrorWithData
import band.effective.office.elevator.domain.models.WorkSpace
import band.effective.office.elevator.domain.models.WorkspaceZone
import band.effective.office.elevator.domain.models.toDomainZone
import band.effective.office.elevator.domain.models.toDomain
import band.effective.office.elevator.domain.repository.WorkspaceRepository
import band.effective.office.elevator.ui.booking.models.WorkSpaceType
import band.effective.office.elevator.utils.localDateTimeToUnix
import band.effective.office.elevator.utils.map
import band.effective.office.network.api.Api
import band.effective.office.network.dto.WorkspaceDTO
import band.effective.office.network.dto.WorkspaceZoneDTO
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDateTime

class WorkspaceRepositoryImpl(
    private val api: Api
) : WorkspaceRepository {

    private val lastZoneResponse =
        MutableStateFlow<Either<ErrorWithData<List<WorkspaceZone>>, List<WorkspaceZone>>>(
            Either.Error(
                ErrorWithData(
                    error = ErrorResponse(code = 404, description = ""),
                    saveData = null
                )
            )
        )

    private val lastWorkSpaceResponse =
        MutableStateFlow<Either<ErrorWithData<List<WorkSpace>>, List<WorkSpace>>>(
            Either.Error(
                ErrorWithData(
                    error = ErrorResponse(code = 404, description = ""),
                    saveData = null
                )
            )
        )

    override suspend fun getZones(): Flow<Either<ErrorWithData<List<WorkspaceZone>>, List<WorkspaceZone>>> =
        flow {
            val response = api.getZones().convertZone(lastZoneResponse.value)
            lastZoneResponse.update { response }
            emit(response)
        }

    override suspend fun getWorkFreeWorkSpace(
        tag: String,
        freeFrom: LocalDateTime?,
        freeUntil: LocalDateTime?
    ): Flow<Either<ErrorWithData<List<WorkSpace>>, List<WorkSpace>>> = flow {
        val response = api.getWorkspaces(
            tag = tag,
            freeFrom = localDateTimeToUnix(freeFrom),
            freeUntil = localDateTimeToUnix(freeUntil)
        ).convertWorkspace(lastWorkSpaceResponse.value)
        lastWorkSpaceResponse.update { response }
        emit(response)
    }

    private fun Either<ErrorResponse, List<WorkspaceZoneDTO>>.convertZone(
        oldValue: Either<ErrorWithData<List<WorkspaceZone>>, List<WorkspaceZone>>
    ) =
        map(errorMapper = { error ->
            ErrorWithData(
                error = error, saveData = when (oldValue) {
                    is Either.Error -> oldValue.error.saveData
                    is Either.Success -> oldValue.data
                }
            )
        },
            successMapper = { zones ->
                zones.toDomainZone()
            }
        )

    private fun Either<ErrorResponse, List<WorkspaceDTO>>.convertWorkspace(
        oldValue: Either<ErrorWithData<List<WorkSpace>>, List<WorkSpace>>
    ) =
        map(errorMapper = { error ->
            ErrorWithData(
                error = error, saveData = when (oldValue) {
                    is Either.Error -> oldValue.error.saveData
                    is Either.Success -> oldValue.data
                }
            )
        },
            successMapper = { zones ->
                zones.toDomain()
            }
        )
}