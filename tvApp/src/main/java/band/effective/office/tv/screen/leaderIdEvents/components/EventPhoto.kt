package band.effective.office.tv.screen.leaderIdEvents.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import band.effective.office.tv.R
import band.effective.office.tv.domain.model.leaderId.LeaderIdEventInfo
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun EventPhoto(eventInfo: LeaderIdEventInfo) {
    val photo = eventInfo.photoUrl ?: R.drawable.events_placeholder
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(photo)
            .crossfade(true)
            .build(),
        contentDescription = eventInfo.name,
        Modifier
            .fillMaxHeight()
            .padding(start = 20.dp),
    )
}