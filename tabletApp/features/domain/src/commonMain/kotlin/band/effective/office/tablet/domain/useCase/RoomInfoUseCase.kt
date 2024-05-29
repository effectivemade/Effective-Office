package band.effective.office.tablet.domain.useCase

import band.effective.office.network.model.Either
import band.effective.office.tablet.domain.model.ErrorWithData
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.repository.RoomRepository
import band.effective.office.tablet.utils.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map
import java.util.GregorianCalendar

/**Use case for get info about room*/
class RoomInfoUseCase(private val repository: RoomRepository) {
    /**Get all rooms names*/
    suspend fun getRoomsNames(): List<String> {
        return when (val rooms = repository.getRoomsInfo()) {
            is Either.Error -> rooms.error.saveData?.map { it.name } ?: listOf()
            is Either.Success -> rooms.data.map { it.name }
        }
    }
    /**Update repository cache*/
    suspend fun updateCache() = repository.updateCashe()
    /**get info about all rooms*/
    suspend operator fun invoke() = repository.getRoomsInfo().mapRoomsInfo()
    /**get update room flow
     * @param scope scope for collect updates from server*/
    fun subscribe(scope: CoroutineScope) =
        repository.subscribeOnUpdates(scope).map { it.mapRoomsInfo() }

    /**Get info about room
     * @param room room name*/
    suspend fun getRoom(room: String) = invoke().map(
        errorMapper = {
            val save = it.saveData
            val eventInfo: RoomInfo =
                save?.firstOrNull { it.name == room } ?: RoomInfo.defaultValue
            ErrorWithData(it.error, eventInfo)
        },
        successMapper = {
            it.firstOrNull() { it.name == room } ?: RoomInfo.defaultValue
        }
    )

    private fun Either<ErrorWithData<List<RoomInfo>>, List<RoomInfo>>.mapRoomsInfo() =
        map(
            errorMapper = { error ->
                val save = error.saveData
                if (save != null)
                    error.copy(saveData = save.map { event ->
                        event.copy(eventList = event.eventList.filter { it.startTime > GregorianCalendar() })
                    }) else error
            },
            successMapper = { data ->
                data.map { it.copy(eventList = it.eventList.filter { it.startTime > GregorianCalendar() }) }
            })

}
