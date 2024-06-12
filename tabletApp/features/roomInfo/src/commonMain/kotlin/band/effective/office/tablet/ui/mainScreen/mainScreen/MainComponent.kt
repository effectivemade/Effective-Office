package band.effective.office.tablet.ui.mainScreen.mainScreen

import android.os.Build
import androidx.annotation.RequiresApi
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.domain.model.Slot
import band.effective.office.tablet.domain.useCase.CancelUseCase
import band.effective.office.tablet.domain.useCase.DeleteCachedEventUseCase
import band.effective.office.tablet.ui.freeSelectRoom.FreeSelectRoomComponent
import band.effective.office.tablet.ui.mainScreen.mainScreen.store.MainFactory
import band.effective.office.tablet.ui.mainScreen.mainScreen.store.MainStore
import band.effective.office.tablet.ui.mainScreen.slotComponent.SlotComponent
import band.effective.office.tablet.ui.mainScreen.slotComponent.store.SlotStore
import band.effective.office.tablet.ui.modal.ModalWindow
import band.effective.office.tablet.ui.updateEvent.UpdateEventComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.N)
class MainComponent(
    private val componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    val onSettings: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    val cancelUseCase by inject<CancelUseCase>()
    val deleteCachedEventUseCase: DeleteCachedEventUseCase by inject()

    val slotComponent = SlotComponent(
        componentContext = componentContext,
        storeFactory = storeFactory,
        roomName = {
            state.value.run {
                with(if (roomList.isNotEmpty()) roomList[indexSelectRoom] else RoomInfo.defaultValue) { name }
            }
        },
        openBookingDialog = { event, room ->
            mainStore.accept(
                intent = MainStore.Intent.OnChangeEventRequest(
                    eventInfo = event
                )
            )
        }
    )

    private val navigation = SlotNavigation<ModalWindowsConfig>()

    @RequiresApi(Build.VERSION_CODES.O)
    val modalWindowSlot = childSlot(
        source = navigation,
        childFactory = ::childFactory
    )

    fun openModalWindow(dist: ModalWindowsConfig) {
        navigation.activate(dist)
    }

    fun closeModalWindow() {
        navigation.dismiss()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun childFactory(
        modalWindows: ModalWindowsConfig,
        componentContext: ComponentContext
    ): ModalWindow {
        return when (modalWindows) {
            is ModalWindowsConfig.FreeRoom -> FreeSelectRoomComponent(
                componentContext = componentContext,
                storeFactory = storeFactory,
                eventInfo = modalWindows.event,
                onCloseRequest = { closeModalWindow() })

            is ModalWindowsConfig.UpdateEvent -> UpdateEventComponent(
                componentContext = componentContext,
                storeFactory = storeFactory,
                event = modalWindows.event,
                room = modalWindows.room,
                isFastBooking = modalWindows.isFastBooking,
                onDelete = { slot ->
                    slotComponent.sendIntent(SlotStore.Intent.Delete(slot, {
                        CoroutineScope(Dispatchers.IO).launch {
                            (slot as? Slot.EventSlot)?.eventInfo?.apply {
                                deleteCachedEventUseCase(
                                    roomName = state.value.run { roomList[indexSelectRoom].name },
                                    eventInfo = this
                                )
                                cancelUseCase(this)
                            }
                        }
                    }))

                },
                onCloseRequest = { closeModalWindow() },
                onTempLoading = {
                    slotComponent.sendIntent(SlotStore.Intent.Loading(Slot.LoadingEventSlot(start = it.startTime, finish = it.finishTime, it)))
                }
            )
        }
    }


    private val mainStore = instanceKeeper.getStore {
        MainFactory(
            storeFactory = storeFactory,
            navigate = ::openModalWindow,
            updateRoomInfo = {
                roomInfo, date->
                updateComponents(roomInfo, date)
            },
            updateDate = ::updateDate
        ).create()
    }

    private fun updateComponents(roomInfo: RoomInfo, date: Calendar) {
        slotComponent.sendIntent(
            SlotStore.Intent.UpdateRequest(
                room = roomInfo.name,
                refresh = false
            )
        )
        slotComponent.sendIntent(
            SlotStore.Intent.UpdateDate(date)
        )
    }

    private fun updateDate(date: Calendar) {
        slotComponent.sendIntent(
            SlotStore.Intent.UpdateDate(date)
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = mainStore.stateFlow
    val label = mainStore.labels

    fun sendIntent(intent: MainStore.Intent) {
        mainStore.accept(intent)
    }

    sealed interface ModalWindowsConfig : Parcelable {
        @Parcelize
        data class UpdateEvent(
            val event: EventInfo,
            val room: String,
            val isFastBooking: Boolean = false
        ) : ModalWindowsConfig

        @Parcelize
        data class FreeRoom(val event: EventInfo) : ModalWindowsConfig
    }
}