package band.effective.office.elevator.ui.employee.aboutEmployee

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.EffectiveOutlinedButton
import band.effective.office.elevator.components.InfoAboutUserUIComponent
import band.effective.office.elevator.components.LoadingIndicator
import band.effective.office.elevator.components.ModalCalendarDateRange
import band.effective.office.elevator.components.TitlePage
import band.effective.office.elevator.components.generateImageLoader
import band.effective.office.elevator.textGrayColor
import band.effective.office.elevator.ui.employee.aboutEmployee.components.BookingCardUser
import band.effective.office.elevator.ui.employee.aboutEmployee.components.ContactUserUIComponent
import band.effective.office.elevator.ui.employee.aboutEmployee.models.BookingsFilter
import band.effective.office.elevator.ui.employee.aboutEmployee.store.AboutEmployeeStore
import band.effective.office.elevator.ui.main.components.BottomDialog
import band.effective.office.elevator.ui.main.components.CalendarTitle
import band.effective.office.elevator.ui.main.components.FilterButton
import band.effective.office.elevator.ui.models.ReservedSeat
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AboutEmployee(component: AboutEmployeeComponent) {
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
            .background(Color.White)
            .fillMaxSize()
    ) {
        AboutEmployeeContent(
            isLoading = state.isLoading,
            imageUrl = state.user.imageUrl,
            userName = state.user.userName,
            post = state.user.post,
            telegram = state.user.telegram,
            email = state.user.email,
            reservedSeatsList = state.reservedSeatsList,
            dateFiltrationOnReserves = state.dateFiltrationOnReserves,
            filtrationOnReserves = state.filtrationOnReserves,
            beginDate = state.beginDate,
            endDate = state.endDate,
            isLoadingBooking = state.isLoadingBookings,
            bottomSheetState = bottomSheetState,
            onClickOpenPhone = { component.onEvent(AboutEmployeeStore.Intent.TelephoneClicked) },
            onClickOpenTelegram = { component.onEvent(AboutEmployeeStore.Intent.TelegramClicked) },
            onClickOpenSpb = { component.onEvent(AboutEmployeeStore.Intent.TransferMoneyClicked) },
            onClickBack = { component.onOutput(AboutEmployeeComponent.Output.OpenAllEmployee) },
            onClickOpenCalendar = { component.onEvent(AboutEmployeeStore.Intent.OpenCalendarClicked) },
            onClickOpenBottomDialog = { component.onEvent(AboutEmployeeStore.Intent.OpenBottomDialog) },
            onClickCloseBottomDialog = {
                component.onEvent(
                    AboutEmployeeStore.Intent.CloseBottomDialog(
                        it
                    )
                )
            }
        )

        if (showModalCalendar) {
            Dialog(
                onDismissRequest = { component.onEvent(AboutEmployeeStore.Intent.CloseCalendarClicked)},
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) { ModalCalendarDateRange(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .align(Alignment.Center),
                    onClickCansel = { component.onEvent(AboutEmployeeStore.Intent.CloseCalendarClicked) },
                    onClickOk = { component.onEvent(AboutEmployeeStore.Intent.OnClickApplyDate(it)) },
                    currentDate = state.beginDate
                )
            }
        }
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
    reservedSeatsList: List<ReservedSeat>,
    dateFiltrationOnReserves: Boolean,
    filtrationOnReserves: Boolean,
    bottomSheetState: ModalBottomSheetState,
    beginDate: LocalDate,
    endDate: LocalDate?,
    onClickBack: () -> Unit,
    onClickOpenPhone: () -> Unit,
    onClickOpenTelegram: () -> Unit,
    onClickOpenSpb: () -> Unit,
    onClickOpenCalendar: () -> Unit,
    onClickOpenBottomDialog: () -> Unit,
    onClickCloseBottomDialog: (BookingsFilter) -> Unit,
    isLoading: Boolean,
    isLoadingBooking: Boolean
) {
    val imageLoader = generateImageLoader()

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
                modifier = Modifier.background(Color.White).padding(top = 40.dp, bottom = 24.dp)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = onClickBack) {
                        Icon(
                            painter = painterResource(MainRes.images.back_button),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                    TitlePage(
                        title = stringResource(MainRes.strings.about_the_employee),
                        modifier = Modifier.align(Alignment.CenterVertically).padding(start = 16.dp)
                    )
                }
                when (isLoading) {
                    true -> {
                        LoadingIndicator()
                    }

                    false -> {
                        val request = remember(imageUrl) {
                            ImageRequest {
                                data(imageUrl)
                            }
                        }
                        val painter = rememberImagePainter(
                            request = request,
                            imageLoader = imageLoader,
                            placeholderPainter = { painterResource(MainRes.images.logo_default) },
                            errorPainter = { painterResource(MainRes.images.logo_default) }
                        )
                        Row(modifier = Modifier.padding(top = 24.dp)) {
                            Column {
                                InfoAboutUserUIComponent(userName, post)
                                ContactUserUIComponent(
                                    MainRes.images.icon_telegram,
                                    telegram,
                                    modifier = Modifier.padding(top = 18.dp)
                                )
                                ContactUserUIComponent(
                                    MainRes.images.mail,
                                    email,
                                    modifier = Modifier.padding(top = 10.dp)
                                )
                            }
                            Spacer(modifier = Modifier.weight(.1f))
                            Surface(
                                modifier = Modifier.size(88.dp),
                                shape = CircleShape,
                                color = ExtendedThemeColors.colors.purple_heart_100
                            ) {
                                Image(
                                    modifier = Modifier.fillMaxSize(),
                                    painter = painter,
                                    contentScale = ContentScale.Crop,
                                    contentDescription = null,
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    EffectiveOutlinedButton(
                        MainRes.images.icon_call,
                        text = null,
                        onClick = onClickOpenPhone,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    EffectiveOutlinedButton(
                        MainRes.images.icon_telegram,
                        text = null,
                        onClick = onClickOpenTelegram,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    EffectiveOutlinedButton(
                        MainRes.images.spb_icon,
                        text = MainRes.strings.transfer,
                        onClick = onClickOpenSpb,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
            when (isLoadingBooking) {
                true -> LoadingIndicator()
                false -> {
                    Column(
                        modifier = Modifier.background(MaterialTheme.colors.onBackground),
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
                                color = ExtendedThemeColors.colors.blackColor,
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(vertical = 24.dp).padding(end = 16.dp),
                                fontWeight = FontWeight(500)
                            )
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                CalendarTitle(
                                    onClickOpenCalendar = onClickOpenCalendar,
                                    fromMainScreen = false,
                                    beginDate = beginDate,
                                    endDate = endDate
                                )
                                FilterButton(
                                    onClickOpenBottomSheetDialog = onClickOpenBottomDialog
                                )
                            }
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
            }
        }
    }
}

@Composable
fun NoReservationsThisDate(noThisDayReservations: Boolean, filtration: Boolean) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
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
            fontSize = 15.sp,
            color = textGrayColor,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ReservationsOnThisDate(reservedSeatsList: List<ReservedSeat>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        items(reservedSeatsList) { seat ->
            BookingCardUser(
                seat
            )
        }
    }
}