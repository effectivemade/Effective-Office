package band.effective.office.tablet.network.repository

import band.effective.office.network.model.Either
import band.effective.office.tablet.domain.model.ErrorWithData
import band.effective.office.tablet.domain.model.RoomInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

/**Repository for get information about room*/
interface RoomRepository {
    /**Get list all rooms*/
    suspend fun getRoomsInfo(): Either<ErrorWithData<List<RoomInfo>>, List<RoomInfo>>

    fun subscribeOnUpdates(
        scope: CoroutineScope
    ): Flow<Either<ErrorWithData<List<RoomInfo>>, List<RoomInfo>>>

    suspend fun updateCashe()
}