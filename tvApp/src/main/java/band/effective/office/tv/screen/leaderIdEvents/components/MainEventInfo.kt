package band.effective.office.tv.screen.leaderIdEvents.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import band.effective.office.tv.R
import band.effective.office.tv.domain.model.leaderId.LeaderIdEventInfo
import band.effective.office.tv.ui.theme.robotoFontFamily

@Composable
fun MainEventInfo(eventInfo: LeaderIdEventInfo) {
    Column(
        modifier = Modifier.fillMaxWidth(0.5f)
    ) {
        Text(
            text = eventInfo.name,
            color = Color.White,
            fontSize = 30.sp,
            fontFamily = robotoFontFamily()
        )
        TextWithCaptionAndIcon(
            resourceId = R.drawable.user,
            text = eventInfo.organizer ?: "Какая-то неопознанная колебяка с района",
            caption = "Организатор",
            fontSize = 12.sp,
            iconSize = 19.dp,
            modifier = Modifier.padding(vertical = 14.dp)
        )
        if (eventInfo.speakers != null && eventInfo.speakers.isNotEmpty()) {
            TextWithCaptionAndIcon(
                resourceId = R.drawable.mic,
                text = speakersName(eventInfo.speakers),
                caption = "Спикеры",
                fontSize = 12.sp,
                iconSize = 19.dp
            )
        }
    }
}

fun speakersName(speakers: List<String>): String{
    var result = ""
    var speakerCounter = 0
    for (speaker in speakers) {
        if (speakerCounter >= 3) break
        result += "$speaker, "
        speakerCounter++
    }
    return result.trim(',',' ')
}