package band.effective.office.elevator.ui.bottomSheets.bookingSheet.bookPeriod.store

import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.domain.models.TypeEndPeriodBooking
import com.arkivanov.mvikotlin.core.store.Store
import dev.icerock.moko.resources.StringResource
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

interface BookPeriodStore : Store<BookPeriodStore.Intent, BookPeriodStore.State, BookPeriodStore.Label> {
    sealed interface Intent {
        data object OnSwitchAllDay : Intent
        data class InputTime(val time: LocalTime, val isStart: Boolean) : Intent
        data class InputDate(val selectDates: List<LocalDate>) : Intent
        data class InputPeriod(val bookingPeriod: BookingPeriod,/* val repeatBooking: StringResource*/) : Intent
        data class InputDayOfEndPeriod(val dateOfEndPeriod: LocalDate) : Intent
        data class InputEndType(val endPeriodBookingType: TypeEndPeriodBooking) : Intent
        data class OnChangeTemplateFrequency(val bookingPeriod: BookingPeriod) : Intent
        data class OnChangeCustomFrequency (
            val bookingPeriod: BookingPeriod,
            val typeEndPeriodBooking: TypeEndPeriodBooking
        ) : Intent
    }

    sealed interface Label {

        data class ShowToast(val message: String) : Label
    }

    data class State(
        val bookingId: String,
        val startDate: LocalDate,
        val startTime: LocalTime,
        val finishDate: LocalDate,
        val finishTime: LocalTime,
        val repeatBooking: StringResource,
        val switchChecked: Boolean,
        val dateOfEndPeriod: LocalDate,
        val bookingPeriod: BookingPeriod,
        val endPeriodBookingType: TypeEndPeriodBooking
    )
}