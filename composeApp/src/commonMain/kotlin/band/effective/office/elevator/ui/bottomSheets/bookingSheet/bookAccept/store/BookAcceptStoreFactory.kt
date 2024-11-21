package band.effective.office.elevator.ui.bottomSheets.bookingSheet.bookAccept.store

import band.effective.office.elevator.domain.entity.BookingInteract
import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.domain.models.CreatingBookModel
import band.effective.office.network.model.Either
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BookAcceptStoreFactory(
    private val storeFactory: StoreFactory,
    private val initState: BookAcceptStore.State,
    private val close: () -> Unit,
    private val onMainScreen: () -> Unit
) : KoinComponent {

    private val bookingInteract: BookingInteract by inject()
    fun create(): BookAcceptStore = object : BookAcceptStore,
        Store<BookAcceptStore.Intent, BookAcceptStore.State, Nothing> by storeFactory.create(
            name = "BookAcceptStore",
            initialState = initState,
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Message {
        object StartBooking : Message
        data class FinishBooking(val isSuccess: Boolean) : Message
        object CloseModal : Message
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<BookAcceptStore.Intent, Nothing, BookAcceptStore.State, Message, Nothing>() {
        override fun executeIntent(
            intent: BookAcceptStore.Intent,
            getState: () -> BookAcceptStore.State
        ) {
            when (intent) {
                BookAcceptStore.Intent.OnAccept -> {
                    dispatch(Message.StartBooking)
                    accept(getState())
                }

                BookAcceptStore.Intent.OnClose -> close()

                is BookAcceptStore.Intent.CloseModal -> {
                    dispatch(Message.CloseModal)
                    if (intent.withSheet) close()
                }

                BookAcceptStore.Intent.SwitchOnMain -> {
                    dispatch(Message.CloseModal)
                    onMainScreen()
                }
            }
        }

        private fun accept(state: BookAcceptStore.State) = scope.launch {
            val startDate = state.dateOfStart
            val endDate = state.dateOfEnd

            Napier.d { "book period: ${state.bookingPeriod is BookingPeriod.EveryWorkDay}" }
            bookingInteract.create(
                creatingBookModel = CreatingBookModel(
                    workSpaceId = state.bookingId, //TODO(Replace with value from DB)
                    dateOfStart = startDate,
                    dateOfEnd = if (endDate.date != startDate.date)
                        LocalDateTime(date = startDate.date, time = endDate.time)
                    else
                        endDate,
                    bookingPeriod = state.bookingPeriod,
                    typeOfEndPeriod = state.typeEndPeriodBooking
                )
            ).collect { response ->
                when (response) {
                    is Either.Success -> dispatch(Message.FinishBooking(true))
                    is Either.Error -> dispatch(Message.FinishBooking(false))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<BookAcceptStore.State, Message> {
        override fun BookAcceptStore.State.reduce(msg: Message): BookAcceptStore.State =
            when (msg) {
                is Message.FinishBooking -> copy(modalState = if (msg.isSuccess) BookAcceptStore.ModalState.SUCCESS else BookAcceptStore.ModalState.ERROR)
                Message.StartBooking -> copy(modalState = BookAcceptStore.ModalState.LOADING)
                Message.CloseModal -> copy(modalState = BookAcceptStore.ModalState.HIDDEN)
            }

    }
}