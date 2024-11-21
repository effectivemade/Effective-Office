package band.effective.office.elevator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.MainRes
import dev.icerock.moko.resources.compose.stringResource
import epicarchitect.calendar.compose.basis.EpicMonth
import epicarchitect.calendar.compose.basis.config.rememberMutableBasisEpicCalendarConfig
import epicarchitect.calendar.compose.datepicker.config.rememberEpicDatePickerConfig
import epicarchitect.calendar.compose.datepicker.state.rememberEpicDatePickerState
import epicarchitect.calendar.compose.pager.config.rememberEpicCalendarPagerConfig
import kotlinx.datetime.LocalDate

@Composable
fun ModalCalendar(
    currentDate: LocalDate,
    onClickOk: (LocalDate?) -> Unit,
    onClickCansel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val calendarConfig = rememberMutableBasisEpicCalendarConfig(
        contentColor = Color.Black
    )

    val state = rememberEpicDatePickerState(
        config = rememberEpicDatePickerConfig(
            pagerConfig = rememberEpicCalendarPagerConfig(
                basisConfig = calendarConfig
            ),
            selectionContentColor = MaterialTheme.colors.onPrimary,
            selectionContainerColor = MaterialTheme.colors.secondaryVariant,
        ),
        initialMonth = EpicMonth(year = currentDate.year, month = currentDate.month),
        selectedDates = listOf(currentDate)
    )

    Column(
        modifier = modifier
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ){
        Calendar(state = state)
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            OutlinedPrimaryButton(
                onClick = onClickCansel,
                title = MainRes.strings.cansel,
                modifier = Modifier.weight(.1f),
                roundedCorner = 8.dp,
                padding = 12.dp
            )
            Spacer(modifier = Modifier.width(16.dp))
            EffectiveButton(
                buttonText =  stringResource(MainRes.strings.ok),
                modifier =  Modifier.weight(.1f),
                onClick = { onClickOk(state.selectedDates.firstOrNull())},
                roundedCorner = 8.dp,
                contentPadding = 12.dp
            )
        }
    }
}