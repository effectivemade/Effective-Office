package band.effective.office.tablet.domain.useCase

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.util.GregorianCalendar
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes

/**timer for update when start/finish event in room*/
class UpdateUseCase(
    private val timerUseCase: TimerUseCase,
    private val roomInfoUseCase: RoomInfoUseCase
) {
    /**flow for update when start/finish event in room*/
    fun updateFlow() = flow {
        while (true) {
            val roomInfo = roomInfoUseCase.getCurrentRooms()
            if (roomInfo.isNotEmpty()) {
                val timeToStartNextEvent = roomInfo
                    .map { roomInfo -> roomInfo.eventList }
                    .flatten()
                    .minByOrNull { it.startTime }
                    ?.run {  (startTime.timeInMillis - GregorianCalendar().timeInMillis).milliseconds  } ?: 1.minutes
                val timeToFinishCurrentEvent = roomInfo
                    .mapNotNull { roomInfo -> roomInfo.currentEvent }
                    .minByOrNull { it.startTime }
                    ?.run { (finishTime.timeInMillis - GregorianCalendar().timeInMillis).milliseconds }
                    ?: 1.minutes
                val minDelay = min(timeToStartNextEvent, timeToFinishCurrentEvent)
                val delay = if (minDelay.isNegative()) 1.minutes else minDelay
                timerUseCase.timerFlow(delay).first().apply { emit(0) }
            } else {
                timerUseCase.timerFlow(1.minutes).first().apply { emit(0) }
            }
        }
    }

    private fun min(first: Duration, second: Duration): Duration =
        if (first < second) first else second
}