package band.effective.office.tablet.ui.mainScreen.slotComponent.store

import android.os.Build
import androidx.annotation.RequiresApi
import band.effective.office.tablet.domain.OfficeTime
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.Organizer
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.domain.model.Slot
import band.effective.office.tablet.domain.useCase.RoomInfoUseCase
import band.effective.office.tablet.domain.useCase.SlotUseCase
import band.effective.office.tablet.domain.useCase.TimerUseCase
import band.effective.office.tablet.ui.mainScreen.slotComponent.model.SlotUi
import band.effective.office.tablet.utils.BootstrapperTimer
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Calendar
import java.util.GregorianCalendar
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes

class SlotStoreFactory(
    private val storeFactory: StoreFactory,
    private val roomName: () -> String,
    private val openBookingDialog: (event: EventInfo, room: String) -> Unit,
) : KoinComponent {
    private val slotUseCase: SlotUseCase by inject()
    private val roomInfoUseCase: RoomInfoUseCase by inject()
    private val timerUseCase: TimerUseCase by inject()
    private val updateTimer = BootstrapperTimer<Action>(timerUseCase)

    @RequiresApi(Build.VERSION_CODES.N)
    fun create(): SlotStore = object : SlotStore,
        Store<SlotStore.Intent, SlotStore.State, Nothing> by storeFactory.create(
            name = "SlotStore",
            bootstrapper = bootstrapper(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl,
            initialState = SlotStore.State.initValue
        ) {}

    @RequiresApi(Build.VERSION_CODES.N)
    @OptIn(ExperimentalMviKotlinApi::class)
    private fun bootstrapper() = coroutineBootstrapper {
        updateTimer.init(this, 15.minutes) {
            withContext(Dispatchers.Main) {
                roomInfoUseCase.getRoom(roomName())?.let {
                    dispatch(Action.UpdateSlots(getUiSlots(it)))
                }
            }
        }
        launch {
            roomInfoUseCase.getRoom(roomName())?.let {
                dispatch(
                    Action.UpdateSlots(
                        getUiSlots(it)
                    )
                )
            }
        }
        launch(Dispatchers.IO) {
            roomInfoUseCase.subscribe().collect { roomsInfo ->
                    val roomInfo = roomsInfo.firstOrNull { it.name == roomName() }
                        ?: return@collect

                    withContext(Dispatchers.Main) {
                        dispatch(Action.UpdateSlots(getUiSlots(roomInfo)))
                    }
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getUiSlots(
        roomInfo: RoomInfo,
        start: Calendar = GregorianCalendar(),
        finish: Calendar = OfficeTime.finishWorkTime(start.clone() as Calendar),
    ) = getSlots(roomInfo, start, finish).map {
        when (it) {
            is Slot.EmptySlot -> SlotUi.SimpleSlot(it)
            is Slot.EventSlot -> SlotUi.SimpleSlot(it)
            is Slot.MultiEventSlot -> SlotUi.MultiSlot(
                slot = it,
                subSlots = it.events.map { slot -> SlotUi.NestedSlot(slot) },
                isOpen = false
            )

            is Slot.LoadingEventSlot -> SlotUi.LoadingSlot(it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getSlots(
        roomInfo: RoomInfo,
        start: Calendar = GregorianCalendar(),
        finish: Calendar = OfficeTime.finishWorkTime(start.clone() as Calendar),
    ): List<Slot> {
         return slotUseCase.getSlots(
            currentEvent = roomInfo.currentEvent,
            events = roomInfo.eventList,
            start = start.apply {
                val round = get(Calendar.MINUTE) % 15
                val adding = if (round != 0) (15 - round) else 0
                add(Calendar.MINUTE, adding)
                // This will probably fix the issue
                // with undisplayed bookings
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            },
            finish = finish
        )
    }

    private inner class ExecutorImpl() :
        CoroutineExecutor<SlotStore.Intent, Action, SlotStore.State, Message, Nothing>() {
        override fun executeAction(action: Action, getState: () -> SlotStore.State) {
            when (action) {
                is Action.UpdateSlots -> {
                    if (action.slots.isNotEmpty()) {
                        updateTimer.restart((action.slots.first().slot.start.timeInMillis - GregorianCalendar().timeInMillis + 60000).milliseconds)
                        dispatch(Message.UpdateSlots(action.slots))
                    }
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.N)
        override fun executeIntent(intent: SlotStore.Intent, getState: () -> SlotStore.State) {
            when (intent) {
                is SlotStore.Intent.ClickOnSlot -> intent.slot.execute(getState())
                is SlotStore.Intent.UpdateRequest -> {
                    if (!getState().slots.any { it is SlotUi.DeleteSlot } || intent.refresh) {
                        updateSlot(intent.room, intent.refresh)
                    }
                }

                is SlotStore.Intent.UpdateDate -> scope.launch {
                    roomInfoUseCase.getRoom(room = roomName())?.let {
                        dispatch(
                            message = Message.UpdateSlots(
                                slots = getUiSlots(
                                    roomInfo = it,
                                    start = maxOf(
                                        OfficeTime.startWorkTime(intent.newDate),
                                        GregorianCalendar()
                                    )
                                ),
                            )
                        )
                    }
                }

                is SlotStore.Intent.Delete -> {
                    val slots = getState().slots
                    var mainSlot: SlotUi.MultiSlot? = null
                    val uiSlot = slots.firstOrNull { it.slot == intent.slot }
                        ?: slots.mapNotNull { (it as? SlotUi.MultiSlot)?.subSlots }.flatten()
                            .firstOrNull { it.slot == intent.slot }
                            ?.apply {
                                mainSlot = slots.mapNotNull { it as? SlotUi.MultiSlot }
                                    .first { it.subSlots.contains(this) }
                            }
                    when {
                        uiSlot == null -> {}
                        mainSlot != null -> {
                            val indexInMultiSlot = mainSlot!!.subSlots.indexOf(uiSlot)
                            val indexMultiSlot = slots.indexOf(mainSlot!!)
                            val newMainSlot = mainSlot!!.copy(
                                subSlots = mainSlot!!.subSlots.toMutableList().apply {
                                    this[indexInMultiSlot] =
                                        SlotUi.DeleteSlot(
                                            slot = intent.slot,
                                            onDelete = intent.onDelete,
                                            original = uiSlot,
                                            index = indexInMultiSlot,
                                            mainSlotIndex = indexMultiSlot
                                        )
                                })
                            dispatch(
                                Message.UpdateSlots(
                                    slots.toMutableList()
                                        .apply { this[indexMultiSlot] = newMainSlot })
                            )
                        }

                        else -> {
                            val index = slots.indexOf(uiSlot)
                            dispatch(
                                Message.UpdateSlots(
                                    slots.toMutableList().apply {
                                        this[index] =
                                            SlotUi.DeleteSlot(
                                                slot = intent.slot,
                                                onDelete = intent.onDelete,
                                                original = uiSlot,
                                                index = index,
                                                mainSlotIndex = null
                                            )
                                    })
                            )
                        }
                    }
                }

                is SlotStore.Intent.OnCancelDelete -> {
                    val slots = getState().slots
                    val original = intent.slot.original
                    val newSlots = if (intent.slot.mainSlotIndex == null) {
                        slots.toMutableList().apply { this[intent.slot.index] = original }
                    } else {
                        val mainSlot =
                            (slots[intent.slot.mainSlotIndex] as SlotUi.MultiSlot).run {
                                copy(
                                    subSlots = subSlots.toMutableList()
                                        .apply { this[intent.slot.index] = original }
                                )
                            }
                        slots.toMutableList().apply { this[intent.slot.mainSlotIndex] = mainSlot }
                    }
                    dispatch(Message.UpdateSlots(newSlots))
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.N)
        private fun updateSlot(roomName: String, refresh: Boolean) {
            scope.launch {
                if (refresh) {
                    withContext(Dispatchers.IO) {
                        roomInfoUseCase.updateCache()
                    }
                }
                roomInfoUseCase.getRoom(roomName)?.let {
                    dispatch(
                        Message.UpdateSlots(
                            getUiSlots(it)
                        )
                    )
                }
            }
        }

        private fun SlotUi.execute(state: SlotStore.State) = when (this) {
            is SlotUi.DeleteSlot -> {}
            is SlotUi.MultiSlot -> openMultislot(this, state)
            is SlotUi.SimpleSlot -> slot.execute(state)
            is SlotUi.NestedSlot -> slot.execute(state)
            is SlotUi.LoadingSlot -> {
                slot.execute(state)
            }
        }

        private fun openMultislot(multislot: SlotUi.MultiSlot, state: SlotStore.State) {
            val slots = state.slots.toMutableList()
            val index = slots.indexOf(multislot)
            slots[index] = multislot.copy(isOpen = !multislot.isOpen)
            dispatch(Message.UpdateSlots(slots))
        }

        private fun Slot.execute(state: SlotStore.State) = when (this) {
            is Slot.EmptySlot -> executeFreeSlot(this)
            is Slot.EventSlot -> executeEventSlot(this)
            is Slot.MultiEventSlot -> {}
            is Slot.LoadingEventSlot -> {}
        }

        private fun executeFreeSlot(slot: Slot.EmptySlot) {
            openBookingDialog(slot.Event(), roomName())
        }

        private fun executeEventSlot(slot: Slot.EventSlot) {
            openBookingDialog(slot.eventInfo, roomName())
        }

        private fun Slot.EmptySlot.Event(): EventInfo =
            EventInfo(
                startTime = start,
                finishTime = finish.run {
                    if (minuteDuration() <= 30)
                        this
                    else (start.clone() as Calendar).apply {
                        add(Calendar.MINUTE, 30)
                    }
                },
                organizer = Organizer.default,
                id = EventInfo.defaultId,
                isLoading = false,
            )

    }

    private object ReducerImpl : Reducer<SlotStore.State, Message> {
        override fun SlotStore.State.reduce(msg: Message): SlotStore.State =
            when (msg) {
                is Message.UpdateSlots -> copy(slots = msg.slots)
            }
    }

    private sealed interface Action {
        data class UpdateSlots(val slots: List<SlotUi>) : Action
    }

    private sealed interface Message {
        data class UpdateSlots(val slots: List<SlotUi>) : Message
    }
}

