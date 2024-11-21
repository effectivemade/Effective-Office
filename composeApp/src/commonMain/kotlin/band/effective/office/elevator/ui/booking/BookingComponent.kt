package band.effective.office.elevator.ui.booking

import band.effective.office.elevator.MainRes
import band.effective.office.elevator.ui.booking.StateConverter.toBookAcceptState
import band.effective.office.elevator.ui.booking.StateConverter.toBookPeriodState
import band.effective.office.elevator.ui.booking.models.WorkSpaceType
import band.effective.office.elevator.ui.booking.models.sheetData.toSelectedBookingPeriod
import band.effective.office.elevator.ui.booking.store.BookingStore
import band.effective.office.elevator.ui.booking.store.BookingStoreFactory
import band.effective.office.elevator.ui.bottomSheets.BottomSheet
import band.effective.office.elevator.ui.bottomSheets.bookingSheet.bookAccept.BookAcceptSheetComponent
import band.effective.office.elevator.ui.bottomSheets.bookingSheet.bookPeriod.BookPeriodSheetComponent
import band.effective.office.elevator.ui.bottomSheets.bookingSheet.chooseZoneSheet.ChooseZoneSheetComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class BookingComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit
) :
    ComponentContext by componentContext {

    private val navigation = SlotNavigation<SheetConfig>()
    val slot: Value<ChildSlot<SheetConfig, BottomSheet>> = childSlot(
        source = navigation,
        childFactory = { bottomSheet, componentContext ->
            when (bottomSheet) {
                SheetConfig.BookAccept -> BookAcceptSheetComponent(
                    componentContext = componentContext,
                    initState = state.value.toBookAcceptState(),
                    close = { closeSheet() },
                    onMainScreen = { onOutput(Output.OpenMainTab) }
                )

                SheetConfig.BookPeriod -> BookPeriodSheetComponent(
                    componentContext = componentContext,
                    initState = state.value.toBookPeriodState(),
                    closeClick = { closeSheet() },
                    accept = {
                        bookingStore.accept(
                            BookingStore.Intent.ApplyBookingPeriodFromSheet(
                                it.toSelectedBookingPeriod()
                            )
                        )
                        closeSheet()
                    },
                    publishLabel = {
                        bookingStore.accept(
                            BookingStore.Intent.HandleLabelFromBookingPeriodSheet(it))
                    }
                )

                SheetConfig.ChooseZone -> ChooseZoneSheetComponent(
                    sheetTile = if (state.value.workSpacesType == WorkSpaceType.WORK_PLACE) MainRes.strings.selection_zones
                    else MainRes.strings.selection_rooms,
                    workSpacesZone = state.value.currentWorkspaceZones,
                    closeSheet = { closeSheet() },
                    confirm = { onEvent(BookingStore.Intent.ChangeSelectedWorkSpacesZone(it)) }
                )
            }
        }
    )

    private val bookingStore = instanceKeeper.getStore {
        BookingStoreFactory(
            storeFactory = storeFactory
        ).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<BookingStore.State> = bookingStore.stateFlow

    val label: Flow<BookingStore.Label> = bookingStore.labels
    fun onEvent(event: BookingStore.Intent) {
        bookingStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        data object OpenMainTab : Output()
    }

    fun openSheet(config: SheetConfig) {
        navigation.activate(config)
    }

    fun closeSheet() {
        navigation.dismiss()
    }

    sealed interface SheetConfig : Parcelable {
        @Parcelize
        data object BookAccept : SheetConfig

        @Parcelize
        data object ChooseZone : SheetConfig

        @Parcelize
        data object BookPeriod : SheetConfig
    }
}