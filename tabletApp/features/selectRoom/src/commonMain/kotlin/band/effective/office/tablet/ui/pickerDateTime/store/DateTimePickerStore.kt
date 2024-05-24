package band.effective.office.tablet.ui.pickerDateTime.store

import com.arkivanov.mvikotlin.core.store.Store
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import java.util.Calendar


interface DateTimePickerStore :
    Store<DateTimePickerStore.Intent, DateTimePickerStore.State, Nothing> {
    sealed interface Intent {
        object CloseModal : Intent
        data class OnChangeDate(val date: LocalDate) : Intent
        data class OnChangeTime(val time: LocalTime) : Intent
    }

    data class State(
        val currentDate: Calendar,
        val dateTimeButtonState: DateTimeButtonState
    ) {
        companion object {
            val default = State(
                currentDate = Calendar.getInstance(),
                dateTimeButtonState = DateTimeButtonState.Enabled()
            )
        }
    }

    sealed interface DateTimeButtonState {
        val isEnabled: Boolean
        data class Enabled(override val isEnabled: Boolean = true) : DateTimeButtonState
        data class TimeBooked(override val isEnabled: Boolean = false) : DateTimeButtonState
        data class IncorrectDate(override val isEnabled: Boolean = false) : DateTimeButtonState
    }
}

