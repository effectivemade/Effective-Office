package band.effective.office.tablet.ui.bookingComponents.pickerDateTime

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import band.effective.office.tablet.ui.common.CrossButtonView
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.header8
import epicarchitect.calendar.compose.basis.EpicMonth
import epicarchitect.calendar.compose.basis.config.rememberBasisEpicCalendarConfig
import epicarchitect.calendar.compose.datepicker.config.rememberEpicDatePickerConfig
import epicarchitect.calendar.compose.datepicker.state.EpicDatePickerState
import epicarchitect.calendar.compose.datepicker.state.rememberEpicDatePickerState
import epicarchitect.calendar.compose.pager.config.rememberEpicCalendarPagerConfig
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import java.text.SimpleDateFormat
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateTimePickerModalView(
    dateTimePickerComponent: DateTimePickerComponent
) {
    val stateDateTime by dateTimePickerComponent.state.collectAsState()

    val epicDatePickerState = rememberEpicDatePickerState(
        config = rememberEpicDatePickerConfig(
            pagerConfig = rememberEpicCalendarPagerConfig(
                basisConfig = rememberBasisEpicCalendarConfig(
                    displayDaysOfAdjacentMonths = true,
                    dayOfMonthViewHeight = 32.dp
                )
            ),
            selectionContainerColor = MaterialTheme.colors.secondary,
        ),
        selectedDates =
        listOf(
            LocalDate(
                year = stateDateTime.currentDate[Calendar.YEAR],
                monthNumber = stateDateTime.currentDate[Calendar.MONTH] + 1,
                dayOfMonth = stateDateTime.currentDate[Calendar.DAY_OF_MONTH],
            )
        ),
        selectionMode = EpicDatePickerState.SelectionMode.Single(1),
        initialMonth = EpicMonth(
            year = stateDateTime.currentDate[Calendar.YEAR],
            month = Month(stateDateTime.currentDate[Calendar.MONTH] + 1)
        )
    )


    DateTimePickerModalView(
        currentDate = stateDateTime.currentDate,
        epicDatePickerState = epicDatePickerState,
        selectedDateTime = stateDateTime.currentDate,
        onCloseRequest = { dateTimePickerComponent.sendIntent(DateTimePickerStore.Intent.CloseModal) },
        onChangeDate = {
            dateTimePickerComponent.sendIntent(DateTimePickerStore.Intent.OnChangeDate(it))
        },
        onChangeTime = {
            dateTimePickerComponent.sendIntent(DateTimePickerStore.Intent.OnChangeTime(it))
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateTimePickerModalView(
    currentDate: Calendar,
    epicDatePickerState: EpicDatePickerState,
    selectedDateTime: Calendar,
    onCloseRequest: () -> Unit,
    onChangeDate: (LocalDate) -> Unit,
    onChangeTime: (LocalTime) -> Unit
) {
    Dialog(
        onDismissRequest = onCloseRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .fillMaxWidth(0.8f)
                .clip(RoundedCornerShape(3))
                .background(LocalCustomColorsPalette.current.elevationBackground)
        ) {
            Column(
                modifier = Modifier.padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CrossButtonView(
                    onDismissRequest = onCloseRequest,
                    modifier = Modifier.fillMaxWidth(1f)
                )
                Row(
                    modifier = Modifier.padding(10.dp).fillMaxHeight(0.8f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TimePickerView(
                        modifier = Modifier.fillMaxWidth(0.3f).fillMaxHeight(),
                        currentDate = currentDate,
                        onSnap = onChangeTime
                    )
                    Spacer(Modifier.width(40.dp))
                    DatePickerView(
                        modifier = Modifier.fillMaxWidth(0.6f).fillMaxHeight(),
                        epicDatePickerState = epicDatePickerState
                    )
                }
                Box(modifier = Modifier.fillMaxSize()) {
                    Button(
                        modifier = Modifier.align(Alignment.Center)
                            .fillMaxWidth(0.3f),
                        onClick = {
                            onCloseRequest()
                        },
                        colors = buttonColors(
                            containerColor = LocalCustomColorsPalette.current.pressedPrimaryButton
                        )
                    ) {
                        Text(
                            text = SimpleDateFormat("dd MMMM HH:mm").format(currentDate.time), /*MainRes.string.apply_date_time_for_booking*/
                            style = header8,
                            color = LocalCustomColorsPalette.current.primaryTextAndIcon,
                        )
                    }
                }
            }
        }
    }
    LaunchedEffect(epicDatePickerState.selectedDates) {
        if (epicDatePickerState.selectedDates.isNotEmpty()) {
            onChangeDate(epicDatePickerState.selectedDates.first())
        }
    }
}
