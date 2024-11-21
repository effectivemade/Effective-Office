package band.effective.office.elevator.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.utils.dateFormat
import band.effective.office.elevator.utils.getCurrentDate
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.datetime.LocalDate

@Composable
fun DateSelection(
    onClickOpenCalendar: () -> Unit,
    onClickOpenBottomDialog: () -> Unit,
    currentDate: LocalDate = getCurrentDate(),
    beginDate: LocalDate,
    endDate: LocalDate?,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(MainRes.strings.nearest_bookings),
            fontSize = 15.sp,
            color = Color.Black,
            modifier = Modifier.wrapContentWidth(),
            fontWeight = FontWeight(500)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            CalendarTitle(
                onClickOpenCalendar = onClickOpenCalendar,
                fromMainScreen = true,
                beginDate = beginDate,
                endDate = endDate,
                currentDate = currentDate
            )
            FilterButton(
                onClickOpenBottomSheetDialog = onClickOpenBottomDialog
            )
        }
    }
}

@Composable
fun CalendarTitle(
    onClickOpenCalendar: () -> Unit,
    fromMainScreen: Boolean,
    currentDate: LocalDate = getCurrentDate(),
    beginDate: LocalDate,
    endDate: LocalDate?,
) {
    Row(
        modifier = Modifier
            .clickable { onClickOpenCalendar() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(MainRes.images.material_calendar_ic),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = if (beginDate == endDate || endDate == null) dateFormat(beginDate)
            else "${dateFormat(beginDate)} - ${dateFormat(endDate)}",
            color = MaterialTheme.colors.secondaryVariant,
            fontSize = 15.sp
        )
    }
}



