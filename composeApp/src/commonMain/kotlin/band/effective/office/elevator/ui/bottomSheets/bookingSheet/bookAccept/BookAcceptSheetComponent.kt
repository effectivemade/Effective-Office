package band.effective.office.elevator.ui.bottomSheets.bookingSheet.bookAccept

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import band.effective.office.elevator.ui.booking.components.modals.BookAccept
import band.effective.office.elevator.ui.booking.components.modals.BookingResult
import band.effective.office.elevator.ui.bottomSheets.BottomSheet
import band.effective.office.elevator.ui.bottomSheets.bookingSheet.bookAccept.store.BookAcceptStore
import band.effective.office.elevator.ui.bottomSheets.bookingSheet.bookAccept.store.BookAcceptStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory


class BookAcceptSheetComponent(
    componentContext: ComponentContext,
    initState: BookAcceptStore.State,
    close: () -> Unit,
) : BottomSheet, ComponentContext by componentContext {

    private val store = BookAcceptStoreFactory(
        storeFactory = DefaultStoreFactory(),
        initState = initState,
        close = close,
    ).create()

    private val navigation = StackNavigation<Child>()

    private val stack = childStack(
        source = navigation,
        initialStack = { listOf(Child.Self) },
        handleBackButton = true,
        childFactory = { child, context -> child }
    )

    @Composable
    override fun SheetContent() {
        val state by store.stateFlow.collectAsState()
        BookAccept(
            onClickCloseBookAccept = {
                store.accept(BookAcceptStore.Intent.OnClose)
            },
            confirmBooking = {
                store.accept(BookAcceptStore.Intent.OnAccept)
                navigation.push(Child.BookingResult)
            },
            seatName = state.seatName,
            startDate = state.dateOfStart,
            finishDate = state.dateOfEnd,
            bookingPeriod = state.bookingPeriod,
            typeEndPeriodBooking = state.typeEndPeriodBooking,
            repeatBooking = state.repeatBooking
        )
    }

    @Composable
    override fun content() {
        val state by store.stateFlow.collectAsState()

        Box {
            Children(stack) {
                when (it.instance) {
                    Child.BookingResult -> {
                        Dialog(
                            content = {
                                BookingResult(
                                    onMain = {
                                        store.accept(BookAcceptStore.Intent.OnClose)
                                   },
                                    close = {
                                        store.accept(
                                            BookAcceptStore.Intent.CloseModal(true)
                                        )
                                        navigation.pop()
                                    },
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                        .align(Alignment.Center),
                                    isLoading = state.modalState == BookAcceptStore.ModalState.LOADING,
                                    isError = state.modalState == BookAcceptStore.ModalState.ERROR
                                )
                            },
                            properties = DialogProperties(usePlatformDefaultWidth = false),
                            onDismissRequest = {
                                store.accept(
                                    BookAcceptStore.Intent.CloseModal(
                                        false
                                    )
                                )
                            }
                        )
                    }

                    else -> {}
                }
            }
        }
    }

    sealed class Child(val type: ChildType) : Parcelable {
        @Parcelize
        data object BookingResult : Child(ChildType.Window)

        @Parcelize
        data object Self : Child(ChildType.Sheet)
    }

    enum class ChildType {
        Window, Sheet
    }
}