package band.effective.office.tablet.ui.fastEvent.store

import java.util.GregorianCalendar
import band.effective.office.tablet.domain.model.EventInfo
import com.arkivanov.mvikotlin.core.store.Store
import java.util.Date

interface FastEventStore : Store<FastEventStore.Intent, FastEventStore.State, Nothing> {

    sealed interface Intent {
        object OnFreeSelectRequest : Intent
        object OnCloseWindowRequest : Intent
    }

    data class State(
        val isLoad: Boolean,
        val isSuccess: Boolean,
        val event: EventInfo,
        val minutesLeft: Int,
        val currentTime: Date
    ) {
        companion object {
            val defaultState =
                State(
                    isLoad = true,
                    isSuccess = false,
                    event = EventInfo.emptyEvent,
                    minutesLeft = 0,
                    currentTime = GregorianCalendar().time
                )
        }
    }
}