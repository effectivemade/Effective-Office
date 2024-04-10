package band.effective.office.elevator.ui.booking

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.bottomSheet.ChildSlotModalBottomSheetLayout
import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.domain.models.TypeEndPeriodBooking
import band.effective.office.elevator.expects.showToast
import band.effective.office.elevator.ui.booking.components.BookingMainContentScreen
import band.effective.office.elevator.ui.booking.models.WorkSpaceUI
import band.effective.office.elevator.ui.booking.store.BookingStore
import band.effective.office.elevator.ui.models.TypesList
import band.effective.office.elevator.utils.isScrollingDown
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import dev.icerock.moko.resources.StringResource
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookingScreen(bookingComponent: BookingComponent) {

    val state by bookingComponent.state.collectAsState()
    val slot by bookingComponent.slot.subscribeAsState()

    LaunchedEffect(bookingComponent) {
        bookingComponent.label.collect { label ->
            when (label) {
                is BookingStore.Label.OpenBookAccept -> bookingComponent.openSheet(BookingComponent.SheetConfig.BookAccept)
                BookingStore.Label.OpenBookPeriod -> bookingComponent.openSheet(BookingComponent.SheetConfig.BookPeriod)
                BookingStore.Label.OpenChooseZone -> bookingComponent.openSheet(BookingComponent.SheetConfig.ChooseZone)
                is BookingStore.Label.ShowToast -> showToast(label.message)
            }
        }
    }

    ChildSlotModalBottomSheetLayout(
        sheetContent = { slot.child?.instance?.SheetContent() },
        sheetContentSlotState = bookingComponent.slot,
        onDismiss = {bookingComponent.closeSheet()},
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = Color.White
    ) {
        Box {
            slot.child?.instance?.content()
            BookingScreenContent(
                workSpaces = state.workSpaces,
                onClickOpenChoseZone = { bookingComponent.onEvent(BookingStore.Intent.OpenChooseZone) },
                onClickOpenBookPeriod = { bookingComponent.onEvent(BookingStore.Intent.OpenBookPeriod) },
                onClickOpenBookAccept = { workSpacesUI ->
                    bookingComponent.onEvent(BookingStore.Intent.OpenBookAccept(value = workSpacesUI))
                },
                startDate = state.selectedStartDate,
                finishDate = state.selectedFinishDate,
                repeatBookings = state.repeatBooking,
                onClickChangeSelectedType = {
                    bookingComponent.onEvent(
                        BookingStore.Intent.ChangeSelectedType(selectedType = it)
                    )
                },
                selectedTypesList = state.selectedType,
                isLoadingWorkspacesList = state.isLoadingListWorkspaces,
                typeOfTypeEndPeriodBooking = state.typeOfEnd,
                bookingPeriod = state.bookingPeriod,

                )
        }
    }
}

@Composable
private fun BookingScreenContent(
    isLoadingWorkspacesList: Boolean,
    workSpaces: List<WorkSpaceUI>,
    onClickOpenBookPeriod: () -> Unit,
    onClickOpenChoseZone: () -> Unit,
    onClickOpenBookAccept: (WorkSpaceUI) -> Unit,
    startDate: LocalDate,
    finishDate: LocalDate,
    repeatBookings: StringResource,
    onClickChangeSelectedType: (TypesList) -> Unit,
    selectedTypesList: TypesList,
    bookingPeriod: BookingPeriod,
    typeOfTypeEndPeriodBooking: TypeEndPeriodBooking
) {
    val scrollState = rememberLazyListState()
    val scrollIsDown = scrollState.isScrollingDown()


    var isExpandedCard by rememberSaveable { mutableStateOf(true) }
    val iconRotationStateCard by animateFloatAsState(targetValue = if (isExpandedCard) 90F else 270F)

    var isExpandedOptions by rememberSaveable { mutableStateOf(true) }
    val iconRotationStateOptions by animateFloatAsState(targetValue = if (isExpandedOptions) 90F else 270F)


    LaunchedEffect(scrollState.isScrollInProgress) {
        if (scrollIsDown) {
            isExpandedOptions = false
        }
    }
    BookingMainContentScreen(
        workSpaces = workSpaces,
        scrollState = scrollState,
        isExpandedCard = isExpandedCard,
        isExpandedOptions = isExpandedOptions,
        iconRotationStateCard = iconRotationStateCard,
        iconRotationStateOptions = iconRotationStateOptions,
        onClickOpenBookAccept = onClickOpenBookAccept,
        onClickOpenBookPeriod = onClickOpenBookPeriod,
        onClickOpenChoseZone = onClickOpenChoseZone,
        onClickExpandedMap = { isExpandedCard = !isExpandedCard },
        onClickExpandedOption = { isExpandedOptions = !isExpandedOptions },
        startDate = startDate,
        finishDate = finishDate,
        bookingPeriod = bookingPeriod,
        typeEndPeriodBooking = typeOfTypeEndPeriodBooking,
        repeatBooking = repeatBookings,
        onClickChangeSelectedType = onClickChangeSelectedType,
        selectedTypesList = selectedTypesList,
        isLoadingWorkspacesList = isLoadingWorkspacesList
    )
}