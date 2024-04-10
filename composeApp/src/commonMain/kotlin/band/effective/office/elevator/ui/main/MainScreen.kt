package band.effective.office.elevator.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.LoadingIndicator
import band.effective.office.elevator.components.ModalCalendarDateRange
import band.effective.office.elevator.components.TitlePage
import band.effective.office.elevator.ui.booking.components.modals.BookingContextMenu
import band.effective.office.elevator.ui.employee.aboutEmployee.models.BookingsFilter
import band.effective.office.elevator.ui.main.components.BookingInformation
import band.effective.office.elevator.ui.main.components.BottomDialog
import band.effective.office.elevator.ui.main.components.ElevatorUIComponent
import band.effective.office.elevator.ui.main.store.MainStore
import band.effective.office.elevator.ui.models.ReservedSeat
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDate


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(component: MainComponent) {

    val state by component.state.collectAsState()
    var isErrorMessageVisible by remember { mutableStateOf(false) }
    var isSuccessMessageVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf(MainRes.strings.something_went_wrong) }
    var selectSeat by remember { mutableStateOf(ReservedSeat.defaultSeat) }
    var showModalCalendar by remember { mutableStateOf(false) }
    var showOptionsMenu by remember { mutableStateOf(false) }
    var showDeleteBooking by remember { mutableStateOf(false) }
    var showModalOptionCard by remember { mutableStateOf(false) }

    val isRefreshing = state.isRefreshing

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            Napier.d { "refresh content" }
            component.onEvent(MainStore.Intent.OnRefreshContent)
        }
    )

    var bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)


    LaunchedEffect(component) {
        component.label.collect { label ->
            when (label) {
                is MainStore.Label.ShowError -> {
                    errorMessage = (label.errorState.message
                        ?: MainRes.strings.something_went_wrong) as StringResource
                    isErrorMessageVisible = true
                    delay(3000)
                    isErrorMessageVisible = false
                }

                MainStore.Label.ShowSuccess -> {
                    isSuccessMessageVisible = true
                    delay(3000)
                    isSuccessMessageVisible = false
                }

                MainStore.Label.ShowOptions -> showOptionsMenu = true
                MainStore.Label.HideOptions -> showOptionsMenu = false

                MainStore.Label.OpenCalendar -> showModalCalendar = true
                MainStore.Label.CloseCalendar -> showModalCalendar = false
                MainStore.Label.OpenFiltersBottomDialog -> bottomSheetState.show()
                MainStore.Label.CloseFiltersBottomDialog -> bottomSheetState.hide()
                is MainStore.Label.OnClickOpenDeleteBooking -> {
                    selectSeat = label.seat
                    showDeleteBooking = true
                }

                MainStore.Label.OnClickCloseDeleteBooking -> showDeleteBooking = false
                MainStore.Label.OnClickOpenEditBooking -> {}
                MainStore.Label.OnClickCloseEditBooking -> {}
                MainStore.Label.OpenBooking -> {
                    component.onOutput(MainComponent.Output.OpenMap)
                }

                is MainStore.Label.DeleteBooking -> {
                    component.onOutput(MainComponent.Output.DeleteBooking(label.id))
                }

                MainStore.Label.CloseOption -> showModalOptionCard = false
            }
        }
    }

    Box(
        modifier = Modifier
            .pullRefresh(pullRefreshState)
            .background(ExtendedThemeColors.colors.whiteColor)
            .fillMaxSize()
    ) {
        MainScreenContent(
            reservedSeats = state.reservedSeats,
            bottomSheetState = bottomSheetState,
            beginDate = state.beginDate,
            endDate = state.endDate,
            onClickBook = { component.onOutput(MainComponent.Output.OpenBookingScreen) },
            onClickOptionMenu = { id ->
                component.onEvent(MainStore.Intent.OnClickShowOption(bookingId = id))
            },
            onClickOpenCalendar = { component.onEvent(MainStore.Intent.OnClickOpenCalendar) },
            onClickOpenBottomDialog = { component.onEvent(MainStore.Intent.OpenFiltersBottomDialog) },
            onClickCloseBottomDialog = {
                component.onEvent(
                    MainStore.Intent.CloseFiltersBottomDialog(
                        it
                    )
                )
            },
            isLoadingBooking = state.isLoading,
            onCallElevator = { component.onEvent(MainStore.Intent.OnClickCallElevator) },
            enableCallElevator = state.enableCallElevator
        )

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            backgroundColor = ExtendedThemeColors.colors.whiteColor,
            modifier = Modifier.align(Alignment.TopCenter),
            contentColor = Color.Black
        )

        if (showModalCalendar) {
            Dialog(
                content = {
                    ModalCalendarDateRange(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        onClickCansel = { component.onEvent(MainStore.Intent.OnClickCloseCalendar) },
                        onClickOk = { component.onEvent(MainStore.Intent.OnClickApplyDate(it)) },
                        currentDate = state.beginDate
                    )
                },
                onDismissRequest = { component.onEvent(MainStore.Intent.OnClickCloseCalendar) },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            )
        }

        if (showOptionsMenu) {
            Dialog(
                content = {
                    BookingContextMenu(
                        onClick = {},
                        modifier = Modifier.padding(16.dp),
                        onClickOpenDeleteBooking = { component.onEvent(MainStore.Intent.OnClickDeleteBooking) },
                        onClickBook = { component.onOutput(MainComponent.Output.OpenMap) }
                    )
                },
                onDismissRequest = { component.onEvent(MainStore.Intent.OnClickHideOption) },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            )
        }

        SnackBarErrorMessage(
            modifier = Modifier.align(Alignment.BottomCenter),
            isVisible = isErrorMessageVisible,
            message = stringResource(errorMessage)
        )
        SnackBarSuccessMessage(
            modifier = Modifier.align(Alignment.BottomCenter),
            isVisible = isSuccessMessageVisible
        )
    }
}

@Composable
fun SnackBarErrorMessage(modifier: Modifier, isVisible: Boolean, message: String) {
    AnimatedVisibility(modifier = modifier, visible = isVisible) {
        Snackbar(modifier.padding(16.dp), backgroundColor = MaterialTheme.colors.error) {
            Text(
                text = message,
                color = ExtendedThemeColors.colors.whiteColor
            )
        }
    }
}

@Composable
private fun SnackBarSuccessMessage(modifier: Modifier, isVisible: Boolean) {
    /*AnimatedVisibility(modifier = modifier, visible = isVisible) {
        Snackbar(
            modifier.padding(16.dp),
            backgroundColor = successGreen
        ) {
            Text(text = stringResource(MainRes.strings.elevator_called_successfully))
        }
    }

     */
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    bottomSheetState: ModalBottomSheetState,
    reservedSeats: List<ReservedSeat>,
    isLoadingBooking: Boolean,
    beginDate: LocalDate,
    endDate: LocalDate?,
    enableCallElevator: Boolean,
    onClickBook: () -> Unit,
    onClickOptionMenu: (String) -> Unit,
    onClickOpenCalendar: () -> Unit,
    onClickOpenBottomDialog: () -> Unit,
    onClickCloseBottomDialog: (BookingsFilter) -> Unit,
    onCallElevator: () -> Unit
) {
    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetState = bottomSheetState,
        sheetContent = {
            BottomDialog(
                Modifier,
                stringResource(MainRes.strings.filter_by_category),
                onClickCloseBottomDialog
            )
        }
    ) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                TitlePage(
                    title = stringResource(MainRes.strings.main),
                    modifier = Modifier.padding(top = 60.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                ElevatorUIComponent(
                    enable = enableCallElevator,
                    onClickCallElevator = onCallElevator
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colors.onBackground)
                    .padding(horizontal = 16.dp),
            ) {
                when (isLoadingBooking) {
                    true -> {
                        LoadingIndicator()
                    }

                    false -> {
                        Napier.d { "showing main content" }
                        BookingInformation(
                            reservedSeats = reservedSeats,
                            beginDate = beginDate,
                            endDate = endDate,
                            onClickBook = onClickBook,
                            onClickOptionMenu = onClickOptionMenu,
                            onClickOpenCalendar = onClickOpenCalendar,
                            onClickOpenBottomDialog = onClickOpenBottomDialog,
                        )
                    }
                }
            }
        }
    }
}

