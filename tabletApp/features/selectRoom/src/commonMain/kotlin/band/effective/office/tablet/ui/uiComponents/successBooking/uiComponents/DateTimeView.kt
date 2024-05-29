package band.effective.office.tablet.ui.uiComponents.successBooking.uiComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.features.selectRoom.MainRes
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.utils.date
import band.effective.office.tablet.utils.time24

@Composable
fun DateTimeView(modifier: Modifier,eventInfo: EventInfo) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = MainRes.string.date_booking.format(
                startTimeDate = eventInfo.startTime.date(),
                startTime = eventInfo.startTime.time24(),
                finishTime = eventInfo.finishTime.time24()
            ),
            style = MaterialTheme.typography.h5,
            color = LocalCustomColorsPalette.current.primaryTextAndIcon
        )
    }
}