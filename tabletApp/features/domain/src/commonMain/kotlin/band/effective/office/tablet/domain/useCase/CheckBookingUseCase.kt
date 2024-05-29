package band.effective.office.tablet.domain.useCase

import android.content.res.Resources.NotFoundException
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.ErrorWithData
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.utils.map
import java.util.Calendar

/**Use case for checking booking room opportunity*/
class CheckBookingUseCase(
    private val roomInfoUseCase: RoomInfoUseCase
) {
    /** get event blocking room for booking
     * @param eventInfo info about event
     * @param room room name
     * @return Event busy with room booking, if room free, return null*/
    suspend operator fun invoke(event: EventInfo, room: String) =
        busyEvents(event, room).map(errorMapper = {
            it.map { it.firstOrNull() }
        }, successMapper = {
            it.firstOrNull()
        })

    /** get events blocking room for booking
     * @param eventInfo info about event
     * @param room room name
     * @return List events busy with room booking, if room free, return null*/
    suspend fun busyEvents(event: EventInfo, room: String) =
        when (val eventList = eventList(room)) {
            is Either.Error -> Either.Error(
                eventList.error.map { it.getBusy(event) }
            )

            is Either.Success -> {
                Either.Success(eventList.data.getBusy(event))
            }
        }
    private suspend fun eventList(room: String): Either<ErrorWithData<List<EventInfo>>, List<EventInfo>> =
        try {
            when (val response = roomInfoUseCase()) {
                is Either.Error -> Either.Error(
                    response.error.map { it.first { it.name == room }.getAllEvents() }
                )

                is Either.Success -> {
                    Either.Success(
                        response.data.first { it.name == room }.getAllEvents()
                    )
                }
            }
        } catch (e: NotFoundException) {
            Either.Error(
                ErrorWithData(
                    ErrorResponse.getResponse(404),
                    null
                )
            )
        }


    /**
     * @return True, if the moment belongs to the time interval between event start and end*/
    private fun Calendar.belong(event: EventInfo) =
        this >= event.startTime && this <= event.finishTime

    private fun RoomInfo.getAllEvents(): List<EventInfo> =
        if (currentEvent != null) eventList + currentEvent!! else eventList

    private fun List<EventInfo>.getBusy(event: EventInfo) =
        filter { it.startTime.belong(event) || event.startTime.belong(it) }
}