package band.effective.office.tablet.ui.bookingComponents.pickerDateTime

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
        val currentDate: Calendar
    ) {
        companion object {
            val default = State(
                currentDate = Calendar.getInstance(),
            )
        }
    }
}

