package band.effective.office.elevator.ui.bottomSheets.bookingSheet.bookPeriod

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.ModalCalendar
import band.effective.office.elevator.components.ModalCalendarDateRange
import band.effective.office.elevator.components.TimePickerModal
import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.ui.booking.components.modals.BookingPeriod
import band.effective.office.elevator.ui.booking.components.modals.BookingRepeat
import band.effective.office.elevator.ui.booking.components.modals.BookingRepeatCard
import band.effective.office.elevator.ui.bottomSheets.BottomSheet
import band.effective.office.elevator.ui.bottomSheets.bookingSheet.bookPeriod.store.BookPeriodStore
import band.effective.office.elevator.ui.bottomSheets.bookingSheet.bookPeriod.store.BookPeriodStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class BookPeriodSheetComponent(
    componentContext: ComponentContext,
    initState: BookPeriodStore.State,
    private val closeClick: () -> Unit,
    private val accept: (BookPeriodStore.State) -> Unit,
    publishLabel: (BookPeriodStore.Label) -> Unit
) : BottomSheet, ComponentContext by componentContext {

    private val navigation = StackNavigation<Child>()
    private val stack = childStack(
        source = navigation,
        initialStack = { listOf(Child.Setting) },
        handleBackButton = true,
        childFactory = { child, context -> child }
    )

    private val store = BookPeriodStoreFactory(
        storeFactory = DefaultStoreFactory(),
        initState = initState,
        publishLabel = publishLabel
    ).create()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Composable
    override fun SheetContent() {
        val state by store.stateFlow.collectAsState()
        val navStack by stack.subscribeAsState()
        navStack.items.lastOrNull() { it.instance.type == ChildType.Sheet }?.run {
            when (instance) {
                Child.CustomPeriod -> BookingRepeat(
                    backButtonClicked = { navigation.pop() },
                    confirmBooking = { period, endType ->
                        store.accept(BookPeriodStore.Intent.OnChangeCustomFrequency(period, endType))
                        navigation.replaceAll(Child.Setting)
                    },
                    onSelected = {},
                    onClickOpenCalendar = { navigation.push(Child.Calendar) },
                    selectedDayOfEnd = state.dateOfEndPeriod
                )

                Child.Setting -> {
                    BookingPeriod(
                        startDate = state.startDate,
                        startTime = state.startTime,
                        finishTime = state.finishTime,
                        repeatBooking = stringResource(state.repeatBooking),
                        switchChecked = state.switchChecked,
                        closeClick = closeClick,
                        onSelectAllDay = { store.accept(BookPeriodStore.Intent.OnSwitchAllDay) },
                        bookDates = { navigation.push(Child.CalendarWithSelect) },
                        bookStartTime = { navigation.push(Child.SelectTime(true)) },
                        bookFinishTime = { navigation.push(Child.SelectTime(false)) },
                        bookingRepeat = { navigation.push(Child.SelectRepeatType) },
                        onClickSearchSuitableOptions = { accept(state) },
                        finishDate = state.finishDate,
                    )
                }
                else -> {}
            }
        }
    }

    @Composable
    override fun content() {
        val state by store.stateFlow.collectAsState()
        Box {
            Children(stack) {
                when (val child = it.instance) {
                    Child.CalendarWithSelect -> {
                        Dialog(
                            content = {
                                ModalCalendarDateRange(
                                    currentDate = state.startDate,
                                    onClickOk = {
                                        navigation.pop()
                                        store.accept(BookPeriodStore.Intent.InputDate(it))
                                    },
                                    onClickCansel = { navigation.pop() },
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                        .align(Alignment.Center)
                                )
                            },
                            properties = DialogProperties(usePlatformDefaultWidth = false),
                            onDismissRequest = { navigation.pop() },
                        )
                    }

                    Child.Calendar -> {
                        Dialog(
                            content = {
                                ModalCalendar(
                                    currentDate = state.dateOfEndPeriod,
                                    onClickOk = {
                                        navigation.pop()
                                        it?.run {
                                            store.accept(
                                                BookPeriodStore.Intent.InputDayOfEndPeriod(
                                                    this
                                                )
                                            )
                                        }
                                    },
                                    onClickCansel = { navigation.pop() },
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp)
                                        .align(Alignment.Center)
                                )
                            },
                            properties = DialogProperties(usePlatformDefaultWidth = false),
                            onDismissRequest = { navigation.pop() },
                        )
                    }

                    Child.SelectRepeatType -> {
                        Dialog(
                            content = {
                                BookingRepeatCard(
                                    onSelected = {
                                        if (it.second == BookingPeriod.Another) {
                                            navigation.push(Child.CustomPeriod)
                                        } else {
                                            navigation.pop()
                                        }
                                        store.accept(
                                            BookPeriodStore.Intent.OnChangeTemplateFrequency(it.second)
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)
                                        .align(Alignment.Center),
                                    weekDays = with(state.bookingPeriod) {
                                        if (this is BookingPeriod.Week)
                                            selectedDayOfWeek
                                        else emptyList()
                                    }
                                )
                            },
                            onDismissRequest = { navigation.pop() },
                            properties = DialogProperties(usePlatformDefaultWidth = false)
                        )
                    }

                    is Child.SelectTime -> {
                        Dialog(
                            content = {
                                TimePickerModal(
                                    startTime = if (child.isStart) state.startTime else state.finishTime,
                                    titleText = stringResource(
                                        if (child.isStart) {
                                            MainRes.strings.take_from
                                        } else {
                                            MainRes.strings.take_before
                                        }
                                    ),
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                        .clip(shape = RoundedCornerShape(16.dp))
                                        .background(Color.White)
                                        .align(Alignment.Center),
                                    onClickCansel = { navigation.pop() },
                                    onClickOk = {
                                        store.accept(
                                            BookPeriodStore.Intent.InputTime(
                                                it,
                                                child.isStart
                                            )
                                        )
                                        navigation.pop()
                                    }
                                )
                            },
                            properties = DialogProperties(usePlatformDefaultWidth = false),
                            onDismissRequest = { navigation.pop() },
                        )
                    }

                    else -> {}
                }
            }
        }
    }

    enum class ChildType {
        Sheet, Window
    }

    sealed class Child(val type: ChildType) : Parcelable {
        @Parcelize
        data object CalendarWithSelect : Child(ChildType.Window)

        @Parcelize
        data object SelectRepeatType : Child(ChildType.Window)

        @Parcelize
        data class SelectTime(val isStart: Boolean) : Child(ChildType.Window)

        @Parcelize
        data object Calendar : Child(ChildType.Window)

        @Parcelize
        data object CustomPeriod : Child(ChildType.Sheet)

        @Parcelize
        data object Setting : Child(ChildType.Sheet)
    }
}