package band.effective.office.tv.screen.leaderIdEvents.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tv.domain.model.leaderId.LeaderIdEventInfo

@Composable
fun EventCard(eventInfo: LeaderIdEventInfo, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(0.5f)
        ) {
            MainEventInfo(eventInfo)
            EventPhoto(eventInfo)
        }
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 50.dp)
        ) {
            AdditionalEventInfo(eventInfo)
            EventQrImage(eventInfo)
        }
    }
}