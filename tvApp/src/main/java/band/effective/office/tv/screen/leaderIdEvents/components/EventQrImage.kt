package band.effective.office.tv.screen.leaderIdEvents.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import band.effective.office.tv.domain.model.leaderId.LeaderIdEventInfo
import band.effective.office.tv.ui.theme.robotoFontFamily
import band.effective.office.tv.utils.rememberQrBitmapPainter

@Composable
fun EventQrImage(eventInfo: LeaderIdEventInfo) {
    Row(Modifier.padding(start = 20.dp)) {
        Image(
            painter = rememberQrBitmapPainter(content = "https://leader-id.ru/events/${eventInfo.id}",curentQr = eventInfo.id),
            contentDescription = eventInfo.name,
        )
        Text(
            text = "Регистрация\n" +
                    "на мероприятие",
            color = MaterialTheme.colors.secondary,
            fontSize = 14.sp,
            fontFamily = robotoFontFamily(),
            modifier = Modifier.padding(start = 30.dp)
        )
    }

}