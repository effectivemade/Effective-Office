package band.effective.office.elevator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import dev.icerock.moko.resources.compose.stringResource
import epicarchitect.calendar.compose.basis.EpicMonth
import epicarchitect.calendar.compose.basis.config.rememberMutableBasisEpicCalendarConfig
import epicarchitect.calendar.compose.datepicker.config.rememberEpicDatePickerConfig
import epicarchitect.calendar.compose.datepicker.state.EpicDatePickerState
import epicarchitect.calendar.compose.datepicker.state.rememberEpicDatePickerState
import epicarchitect.calendar.compose.pager.config.rememberEpicCalendarPagerConfig
import kotlinx.datetime.LocalDate

@Composable
fun ModalCalendarDateRange(
    currentDate: LocalDate,
    onClickOk: (List<LocalDate>) -> Unit,
    onClickCansel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isSelectRange by remember { mutableStateOf(false) }

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
        selectionMode = if (isSelectRange)
            EpicDatePickerState.SelectionMode.Range
        else
            EpicDatePickerState.SelectionMode.Single(),
        initialMonth = EpicMonth(year = currentDate.year, month = currentDate.month),
        selectedDates = listOf(currentDate)
    )

    Column(
        modifier = modifier
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Calendar(state = state)
        Spacer(modifier = Modifier.height(16.dp))
        SwitchedDateRange(
            isSelectRange = isSelectRange,
            onClickSwitch = { isSelectRange = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
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
                buttonText = stringResource(MainRes.strings.ok),
                modifier = Modifier.weight(.1f),
                onClick = { onClickOk(state.selectedDates) },
                roundedCorner = 8.dp,
                contentPadding = 12.dp
            )
        }
    }
}

@Composable
private fun SwitchedDateRange(
    isSelectRange: Boolean,
    onClickSwitch: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // NOTE: This row needed for positioning at the edges
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(MainRes.strings.period_selection),
                style = MaterialTheme.typography.body1.copy(color = Color.Black)
            )
        }
        Switch(
            checked = isSelectRange,
            onCheckedChange = onClickSwitch,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colors.primary,
                uncheckedThumbColor = ExtendedThemeColors.colors.switchColor,
                uncheckedBorderColor = ExtendedThemeColors.colors.transparentColor,
                checkedBorderColor = ExtendedThemeColors.colors.transparentColor
            )
        )
    }
}
