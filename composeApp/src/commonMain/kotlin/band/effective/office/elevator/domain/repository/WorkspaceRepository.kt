package band.effective.office.elevator.domain.repository

import band.effective.office.elevator.domain.models.ErrorWithData
import band.effective.office.elevator.domain.models.WorkSpace
import band.effective.office.elevator.domain.models.WorkspaceZone
import band.effective.office.elevator.ui.booking.models.WorkSpaceType
import band.effective.office.network.model.Either
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

interface WorkspaceRepository {

    suspend fun getZones(): Flow<Either<ErrorWithData<List<WorkspaceZone>>, List<WorkspaceZone>>>

    suspend fun getWorkFreeWorkSpace(
        tag: String,
        freeFrom: LocalDateTime? = null,
        freeUntil: LocalDateTime? = null
    ): Flow<Either<ErrorWithData<List<WorkSpace>>, List<WorkSpace>>>
}