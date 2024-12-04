package band.effective.office.elevator.ui.employee.aboutEmployee

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.EffectiveTheme
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.EmployeeBlock
import band.effective.office.elevator.components.LoadingIndicator
import band.effective.office.elevator.components.TitlePage
import band.effective.office.elevator.components.UserScreen
import band.effective.office.elevator.components.generateImageLoader
import band.effective.office.elevator.expects.setClipboardText
import band.effective.office.elevator.ui.employee.aboutEmployee.components.BookingCardUser
import band.effective.office.elevator.ui.employee.aboutEmployee.components.EmployeeInfo
import band.effective.office.elevator.ui.employee.aboutEmployee.models.BookingsFilter
import band.effective.office.elevator.ui.employee.aboutEmployee.store.AboutEmployeeStore
import band.effective.office.elevator.ui.main.components.BottomDialog
import band.effective.office.elevator.ui.models.ReservedSeat
import band.effective.office.elevator.utils.prettifyPhoneNumber
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AboutEmployeeScreen(component: AboutEmployeeComponent) {
    val state by component.state.collectAsState()
    var showModalCalendar by remember { mutableStateOf(false) }
    var bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    LaunchedEffect(component) {
        component.label.collect { label ->
            when (label) {
                AboutEmployeeStore.Label.OpenCalendar -> showModalCalendar = true
                AboutEmployeeStore.Label.CloseCalendar -> showModalCalendar = false
                AboutEmployeeStore.Label.OpenBottomDialog -> bottomSheetState.show()
                AboutEmployeeStore.Label.CloseBottomDialog -> bottomSheetState.hide()
            }
        }
    }
    Box(
        modifier = Modifier
            .background(EffectiveTheme.colors.background.primary)
            .fillMaxSize()
    ) {
        AboutEmployeeContent(
            imageUrl = state.user.imageUrl,
            userName = state.user.userName,
            post = state.user.post,
            telegram = state.user.telegram,
            email = state.user.email,
            phoneNumber = state.user.phoneNumber,
            bottomSheetState = bottomSheetState,
            onClickBack = { component.onOutput(AboutEmployeeComponent.Output.OpenAllEmployee) },
            onClickCloseBottomDialog = {
                component.onEvent(
                    AboutEmployeeStore.Intent.CloseBottomDialog(
                        it
                    )
                )
            },
            isLoading = state.isLoading,
            isLoadingBooking = state.isLoadingBookings,
            reservedSeatsList = state.reservedSeatsList,
            dateFiltrationOnReserves = state.dateFiltrationOnReserves,
            filtrationOnReserves = state.filtrationOnReserves
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AboutEmployeeContent(
    imageUrl: String?,
    userName: String?,
    post: String?,
    telegram: String?,
    email: String?,
    phoneNumber: String?,
    bottomSheetState: ModalBottomSheetState,
    onClickBack: () -> Unit,
    onClickCloseBottomDialog: (BookingsFilter) -> Unit,
    isLoading: Boolean,
    isLoadingBooking: Boolean,
    reservedSeatsList: List<ReservedSeat>,
    dateFiltrationOnReserves: Boolean,
    filtrationOnReserves: Boolean,
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
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .background(EffectiveTheme.colors.background.tertiary)
                    .padding(top = 40.dp, bottom = 24.dp)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        modifier = Modifier.align(Alignment.CenterStart),
                        onClick = onClickBack,
                    ) {
                        Icon(
                            painter = painterResource(MainRes.images.back_button),
                            contentDescription = stringResource(MainRes.strings.back),
                            tint = EffectiveTheme.colors.icon.secondary,
                            modifier = Modifier.size(24.dp),
                        )
                    }
                    TitlePage(
                        title = stringResource(MainRes.strings.about_the_employee),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.padding(24.dp))
                val localUriHandler = LocalUriHandler.current
                if (isLoading) {
                    LoadingIndicator()
                } else {
                    EmployeeBlock(
                        imageUrl = imageUrl,
                        userName = userName,
                        post = post,
                        telegram = telegram,
                        email = email,
                        phoneNumber = phoneNumber,
                        onOpenUri = { localUriHandler.openUri(it) },
                        onCopyText = { value, label -> setClipboardText(value, label) }
                    )
                }
            }
            if (isLoadingBooking) {
                LoadingIndicator()
            } else {
                BookingBlock(
                    reservedSeatsList = reservedSeatsList,
                    dateFiltrationOnReserves = dateFiltrationOnReserves,
                    filtrationOnReserves = filtrationOnReserves,
                )
            }
        }
    }
}

@Composable
private fun BookingBlock(
    modifier: Modifier = Modifier,
    reservedSeatsList: List<ReservedSeat>,
    dateFiltrationOnReserves: Boolean,
    filtrationOnReserves: Boolean,
) {
    Column(
        modifier = modifier.background(EffectiveTheme.colors.background.tertiary),
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(MainRes.strings.upcoming_bookings),
                color = EffectiveTheme.colors.text.primary,
                style = EffectiveTheme.typography.lMedium,
                modifier = Modifier.padding(vertical = 24.dp).padding(end = 16.dp),
                fontWeight = FontWeight(500)
            )
        }

        if (reservedSeatsList.isEmpty()) {
            NoReservationsThisDate(
                noThisDayReservations = dateFiltrationOnReserves,
                filtration = filtrationOnReserves
            )
        } else {
            ReservationsOnThisDate(
                reservedSeatsList = reservedSeatsList,
            )
        }
    }
}

@Composable
private fun NoReservationsThisDate(
    modifier: Modifier = Modifier,
    noThisDayReservations: Boolean, filtration: Boolean
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(
                if (filtration) {
                    if (noThisDayReservations) MainRes.strings.none_such_booking_seats_on_this_date
                    else MainRes.strings.none_such_booking_seats_by_the_employee
                } else {
                    if (noThisDayReservations) MainRes.strings.none_booking_seats_on_this_date
                    else MainRes.strings.none_booking_seats_by_the_employee
                }
            ),
            style = EffectiveTheme.typography.mMedium,
            color = EffectiveTheme.colors.text.secondary,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun ReservationsOnThisDate(
    modifier: Modifier = Modifier,
    reservedSeatsList: List<ReservedSeat>
) {
    LazyColumn(
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        items(reservedSeatsList) { seat ->
            BookingCardUser(
                seat
            )
        }
    }
}
