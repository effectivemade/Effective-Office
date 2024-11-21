package band.effective.office.tv.screen.leaderIdEvents.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tv.R
import band.effective.office.tv.domain.model.leaderId.LeaderIdEventInfo
import band.effective.office.tv.utils.startEndTimeInterval
import band.effective.office.tv.utils.toEndEventTimeInterval
import java.util.GregorianCalendar

@Composable
fun AdditionalEventInfo(eventInfo: LeaderIdEventInfo) {
    val fontSize = 18.sp
    val iconSize = 29.dp
    Column(
        modifier = Modifier
            .padding(bottom = 50.dp, top = 10.dp, start = 0.dp, end = 0.dp)
            .fillMaxWidth(0.5f)
            .fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (eventInfo.endRegDate != null && eventInfo.endRegDate > GregorianCalendar()) {
            TextWithCaptionAndIcon(
                resourceId = R.drawable.error_circle,
                text = toEndEventTimeInterval(eventInfo.endRegDate, "Регистрация"),
                caption = "",
                fontSize = fontSize,
                iconSize = iconSize
            )
        }
        TextWithCaptionAndIcon(
            resourceId = R.drawable.clock,
            text = startEndTimeInterval(eventInfo.startDateTime, eventInfo.finishDateTime),
            fontSize = fontSize,
            iconSize = iconSize
        )
        TextWithCaptionAndIcon(
            resourceId = R.drawable.location,
            text = if (eventInfo.isOnline) "Онлайн при поддержке Точки кипения" else "Омск, Точка кипения - Омск",
            fontSize = fontSize,
            iconSize = iconSize
        )
    }
}