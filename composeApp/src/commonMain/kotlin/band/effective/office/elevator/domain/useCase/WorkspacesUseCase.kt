package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.domain.models.ErrorWithData
import band.effective.office.elevator.domain.models.WorkSpace
import band.effective.office.elevator.domain.models.WorkspaceZone
import band.effective.office.elevator.domain.models.toUIModel
import band.effective.office.elevator.domain.models.toUIModelZones
import band.effective.office.elevator.domain.repository.WorkspaceRepository
import band.effective.office.elevator.ui.booking.models.WorkSpaceType
import band.effective.office.elevator.ui.booking.models.WorkspaceZoneUI
import band.effective.office.elevator.ui.booking.models.WorkspacesList
import band.effective.office.elevator.ui.booking.models.mapWorkspaceToZone
import band.effective.office.network.model.Either
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import kotlinx.datetime.LocalDateTime

class WorkspacesUseCase (
    private val repository: WorkspaceRepository
) {
    suspend fun getAllWorkspaces() =
        repository.getZones().mapZones().zip(
            getWorkSpaces(tag = WorkSpaceType.MEETING_ROOM.type)
        ) { zones, workspaces ->
            val mutableMap: MutableMap<WorkSpaceType, List<WorkspaceZoneUI>> = mutableMapOf()
            when (zones) {
                is Either.Error -> {}
                is Either.Success ->
                    mutableMap[WorkSpaceType.WORK_PLACE] = zones.data
            }
            when (workspaces) {
                is Either.Error -> {}
                is Either.Success ->
                    mutableMap[WorkSpaceType.MEETING_ROOM] = workspaces.data.mapWorkspaceToZone()
            }

            if (mutableMap.isEmpty()) {
                Either.Error(Exception("can not get zones"))
            } else {
                Either.Success(WorkspacesList (workspaces = mutableMap))
            }
        }


    suspend fun getWorkSpaces(
        tag: String,
        freeFrom: LocalDateTime? = null,
        freeUntil: LocalDateTime? = null
    ) = repository.getWorkFreeWorkSpace(
        tag = tag,
        freeFrom = freeFrom,
        freeUntil = freeUntil
    ).map()

    private fun Flow<Either<ErrorWithData<List<WorkSpace>>, List<WorkSpace>>>.map() =
        this.map { response ->
            when {
                // 400 is a client error, this is simply means
                // that we have problem with zones/booking period
                // (i.e. if none of zones selected)
                response is Either.Error && response.error.error.code != 400 -> Either.Error(
                    ErrorWithData(
                        error = response.error.error,
                        saveData = response.error.saveData?.toUIModel()
                    )
                )
                response is Either.Success -> Either.Success(response.data.toUIModel())
                else -> Either.Success(emptyList())
            }
        }

    private fun Flow<Either<ErrorWithData<List<WorkspaceZone>>, List<WorkspaceZone>>>.mapZones() =
        map { response ->
            when(response) {
                is Either.Error -> Either.Error(
                    ErrorWithData(
                        error = response.error.error,
                        saveData = response.error.saveData?.toUIModelZones()
                    )
                )
                is Either.Success -> Either.Success(response.data.toUIModelZones())
            }
        }
}