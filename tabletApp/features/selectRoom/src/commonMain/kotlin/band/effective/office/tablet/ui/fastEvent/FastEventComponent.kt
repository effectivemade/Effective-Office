package band.effective.office.tablet.ui.fastEvent

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
    val room: String,
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
            room = room,
            eventInfo = eventInfo,
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
        object SuccessModal : ModalConfig

        @Parcelize
        object FailureModal : ModalConfig

        @Parcelize
        object LoadingModal: ModalConfig
    }

}