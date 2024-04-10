package band.effective.office.tablet.ui.updateEvent

import android.os.Build
import androidx.annotation.RequiresApi
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.Slot
import band.effective.office.tablet.ui.bookingComponents.pickerDateTime.DateTimePickerComponent
import band.effective.office.tablet.ui.modal.ModalWindow
import band.effective.office.tablet.ui.updateEvent.store.UpdateEventStore
import band.effective.office.tablet.ui.updateEvent.store.UpdateEventStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi

class UpdateEventComponent(
    private val componentContext: ComponentContext,
    storeFactory: StoreFactory,
    event: EventInfo,
    val room: String,
    onDelete: (Slot) -> Unit,
    onCloseRequest: () -> Unit
) : ComponentContext by componentContext, ModalWindow {

    private val navigation = StackNavigation<ModalConfig>()

    val childStack = childStack(
        source = navigation,
        initialConfiguration = ModalConfig.UpdateModal,
        childFactory = { config, _ -> config },
    )

    private val store = instanceKeeper.getStore {
        UpdateEventStoreFactory(
            storeFactory = storeFactory,
            onCloseRequest = onCloseRequest,
            navigate = { navigation.push(it) },
            room = room,
            onDelete = onDelete
        ).create(defaultValue = event.toState())
    }

    @delegate:RequiresApi(Build.VERSION_CODES.O)
    val dateTimePickerComponent: DateTimePickerComponent by lazy {
        DateTimePickerComponent(
            componentContext = componentContext,
            storeFactory = storeFactory,
            onSelectDate = { store.accept(UpdateEventStore.Intent.OnSetDate(it)) },
            onCloseRequest = { store.accept(UpdateEventStore.Intent.OnCloseSelectDateDialog) },
            initDate = { state.value.date }
        )
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    val state = store.stateFlow

    fun sendIntent(intent: UpdateEventStore.Intent) {
        store.accept(intent)
    }

    private fun EventInfo.toState(): UpdateEventStore.State =
        UpdateEventStore.State.defaultValue.copy(
            date = startTime,
            duration = ((finishTime.time.time - startTime.time.time) / 60000).toInt(),
            selectOrganizer = organizer,
            inputText = organizer.fullName,
            event = this
        )

    sealed interface ModalConfig : Parcelable {
        @Parcelize
        object UpdateModal : ModalConfig

        @Parcelize
        object SuccessModal : ModalConfig

        @Parcelize
        object FailureModal : ModalConfig
    }
}
