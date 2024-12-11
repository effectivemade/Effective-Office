package band.effective.office.elevator.ui.bottomSheets.bookingSheet.bookAccept.store

import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.domain.models.TypeEndPeriodBooking
import com.arkivanov.mvikotlin.core.store.Store
import dev.icerock.moko.resources.StringResource
import kotlinx.datetime.LocalDateTime

interface BookAcceptStore :
    Store<BookAcceptStore.Intent, BookAcceptStore.State, Nothing> {
    sealed interface Intent {
        object OnClose : Intent
        object OnAccept : Intent
        data class CloseModal(val withSheet: Boolean) : Intent
    }

    enum class ModalState {
        HIDDEN, LOADING, SUCCESS, ERROR
    }

    data class State(
        val bookingId: String,
        val seatName: String,
        val dateOfStart: LocalDateTime,
        val dateOfEnd: LocalDateTime,
        val bookingPeriod: BookingPeriod,
        val typeEndPeriodBooking: TypeEndPeriodBooking,
        val repeatBooking: StringResource,
        val modalState: ModalState
    )
}