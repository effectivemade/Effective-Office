package band.effective.office.tablet.ui.bookingComponents.pickerDateTime

import android.os.Build
import androidx.annotation.RequiresApi
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import org.koin.core.component.KoinComponent
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
class DateTimePickerStoreFactory(
    private val storeFactory: StoreFactory,
    private val closeModal: () -> Unit,
    private val accept: (Calendar) -> Unit,
    private val initDate: Calendar
) : KoinComponent {
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

                is DateTimePickerStore.Intent.OnChangeDate -> {
                    val newDate = (getState().currentDate.clone() as Calendar).apply {
                        set(Calendar.YEAR, intent.date.year)
                        set(Calendar.MONTH, intent.date.month.value - 1)
                        set(Calendar.DAY_OF_MONTH, intent.date.dayOfMonth)
                    }
                    dispatch(Message.UpdateDateTime(newDate))
                }

                is DateTimePickerStore.Intent.OnChangeTime -> {
                    val newDate = (getState().currentDate.clone() as Calendar).apply {
                        set(Calendar.HOUR_OF_DAY, intent.time.hour)
                        set(Calendar.MINUTE, intent.time.minute)
                    }
                    dispatch(Message.UpdateDateTime(newDate))
                }
            }
        }

    }

    private object ReducerImpl : Reducer<DateTimePickerStore.State, Message> {
        override fun DateTimePickerStore.State.reduce(msg: Message): DateTimePickerStore.State =
            when (msg) {
                is Message.UpdateDateTime -> copy(currentDate = msg.newValue)
            }
    }
}