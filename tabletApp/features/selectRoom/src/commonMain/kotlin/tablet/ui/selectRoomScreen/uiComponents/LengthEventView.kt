package tablet.ui.selectRoomScreen.uiComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import tablet.domain.model.Booking
import java.util.Calendar

@Composable
fun LengthEventView(modifier: Modifier, shape: RoundedCornerShape, booking: Booking) {

    val hours = getLengthEvent(booking.eventInfo.startTime, booking.eventInfo.finishTime) / 60
    val minutes = getLengthEvent(booking.eventInfo.startTime, booking.eventInfo.finishTime) % 60
    val lengthEvent: String = when(hours){
        0 -> "$minutes мин"
        else -> "${hours}ч ${minutes}мин"
    }

    Card(
        shape = shape,
        backgroundColor = Color(0xFF3A3736)
    ) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = lengthEvent,
                fontSize = 20.sp,
                fontWeight = FontWeight(700),
                fontFamily = FontFamily.SansSerif,
                color = Color(0xFFFAFAFA)
            )
        }
    }
}

private fun getLengthEvent(start: Calendar, finish: Calendar) =
    finish.get(Calendar.MINUTE) - start.get(Calendar.MINUTE)