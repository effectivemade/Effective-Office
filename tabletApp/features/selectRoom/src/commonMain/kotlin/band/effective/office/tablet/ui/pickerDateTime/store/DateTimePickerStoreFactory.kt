package band.effective.office.tablet.ui.pickerDateTime.store

import android.os.Build
import androidx.annotation.RequiresApi
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.useCase.CheckBookingUseCase
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
class DateTimePickerStoreFactory(
    private val storeFactory: StoreFactory,
    private val closeModal: () -> Unit,
    private val accept: (Calendar) -> Unit,
    private val event: EventInfo,
    private val room: String,
    private val duration: Int,
    private val initDate: Calendar,
) : KoinComponent {

    val checkBookingUseCase: CheckBookingUseCase by inject()
    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): DateTimePickerStore =
        object : DateTimePickerStore,
            Store<DateTimePickerStore.Intent, DateTimePickerStore.State, Nothing> by storeFactory.create(
                name = "DateTimePickerStore",
                initialState = DateTimePickerStore.State.default.copy(currentDate = initDate),
                bootstrapper = coroutineBootstrapper { },
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Message {
        data class UpdateDateTime(val newValue: Calendar) : Message
        data class EnableDateButton(val isEnabled: Boolean): Message
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private inner class ExecutorImpl() :
        CoroutineExecutor<DateTimePickerStore.Intent, Nothing, DateTimePickerStore.State, Message, Nothing>() {
        override fun executeIntent(
            intent: DateTimePickerStore.Intent,
            getState: () -> DateTimePickerStore.State
        ) {
            when (intent) {
                is DateTimePickerStore.Intent.CloseModal -> {
                    accept(getState().currentDate)
                    closeModal()
                }

                is DateTimePickerStore.Intent.OnChangeDate -> changeDate(
                    getState,
                    intent.date.year,
                    intent.date.month.value,
                    intent.date.dayOfMonth
                )

                is DateTimePickerStore.Intent.OnChangeTime -> changeTime(
                    getState,
                    intent.time.hour,
                    intent.time.minute
                )
            }
        }

        fun changeDate(
            getState: () -> DateTimePickerStore.State,
            year: Int,
            month: Int,
            dayOfMonth: Int
        ) = scope.launch {
            val newDate = (getState().currentDate.clone() as Calendar).apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month - 1)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            val finishDate = (newDate.clone() as Calendar).apply {
                add(
                    Calendar.MINUTE,
                    duration
                )
            }
            dispatch(Message.UpdateDateTime(newDate))
            checkEnableDateButton(newDate, finishDate)
        }
        fun changeTime(
            getState: () -> DateTimePickerStore.State,
            hour: Int,
            minute: Int
        ) = scope.launch {
            val newDate = (getState().currentDate.clone() as Calendar).apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
            }
            val finishDate = (newDate.clone() as Calendar).apply {
                add(
                    Calendar.MINUTE,
                    duration
                )
            }
            dispatch(Message.UpdateDateTime(newDate))
            checkEnableDateButton(newDate, finishDate)
        }

        suspend fun checkEnableDateButton(
            startDate: Calendar,
            finishDate: Calendar
        ) {
            val busyEvent: List<EventInfo> = checkBookingUseCase.busyEvents(
                event = event.copy(startTime = startDate, finishTime = finishDate),
                room = room
            ).filter { it.startTime != startDate }
            if (busyEvent.isNotEmpty()){
                dispatch(Message.EnableDateButton(false))
            }
            else{
                dispatch(Message.EnableDateButton(true))
            }
        }

    }

    private object ReducerImpl : Reducer<DateTimePickerStore.State, Message> {
        override fun DateTimePickerStore.State.reduce(msg: Message): DateTimePickerStore.State =
            when (msg) {
                is Message.UpdateDateTime -> copy(currentDate = msg.newValue)
                is Message.EnableDateButton -> copy(isEnabledButton = msg.isEnabled)
            }
    }
}