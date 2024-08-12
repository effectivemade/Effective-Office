package band.effective.office.tablet.ui.fastEvent

import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.ui.fastEvent.store.FastEventStore
import band.effective.office.tablet.ui.fastEvent.store.FastEventStoreFactory
import band.effective.office.tablet.ui.modal.ModalWindow
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
import org.koin.core.component.KoinComponent

class FastEventComponent(
    private val componentContext: ComponentContext,
    storeFactory: StoreFactory,
    val eventInfo: EventInfo,
    val selectedRoom: String,
    val rooms: List<String>,
    private val onEventCreation : suspend (EventInfo, String) -> Either<ErrorResponse, EventInfo>,
    private val onRemoveEvent : suspend (EventInfo, String) -> Either<ErrorResponse, String>,
    private val onCloseRequest: () -> Unit
) : ComponentContext by componentContext, KoinComponent, ModalWindow {

    private val navigation = StackNavigation<ModalConfig>()

    val childStack = childStack(
        source = navigation,
        initialConfiguration = ModalConfig.LoadingModal,
        childFactory = { config, _ -> config},
    )

    private val store: FastEventStore = instanceKeeper.getStore {
        FastEventStoreFactory(
            storeFactory = storeFactory,
            navigate = { navigation.push(it) },
            selectedRoom = selectedRoom,
            rooms = rooms,
            eventInfo = eventInfo,
            onEventCreation = onEventCreation,
            onRemoveEvent = onRemoveEvent,
            onCloseRequest = onCloseRequest
        ).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = store.stateFlow

    fun sendIntent(intent: FastEventStore.Intent) {
        store.accept(intent)
    }

    sealed interface ModalConfig : Parcelable {
        @Parcelize
        data class SuccessModal(val room: String) : ModalConfig

        @Parcelize
        data class FailureModal(val room: String) : ModalConfig

        @Parcelize
        object LoadingModal: ModalConfig
    }

}