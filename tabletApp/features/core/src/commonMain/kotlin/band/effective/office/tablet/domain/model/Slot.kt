package band.effective.office.tablet.domain.model

import java.util.Calendar

sealed class Slot {
    abstract val start: Calendar
    abstract val finish: Calendar

    fun minuteDuration() = ((finish.time.time - start.time.time) / 60000).toInt()

    data class EmptySlot(
        override val start: Calendar,
        override val finish: Calendar,
    ) : Slot()

    data class EventSlot(
        override val start: Calendar,
        override val finish: Calendar,
        val eventInfo: EventInfo
    ) : Slot()
    data class MultiEventSlot(
        override val start: Calendar,
        override val finish: Calendar,
        val events: List<EventSlot>
    ) : Slot()
}