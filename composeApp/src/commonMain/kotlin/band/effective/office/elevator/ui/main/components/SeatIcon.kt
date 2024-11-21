package band.effective.office.elevator.ui.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun SeatIcon() {
    Box (
        modifier = Modifier
            .clip(RoundedCornerShape(48.dp))
            .size(44.dp)
           .background(ExtendedThemeColors.colors.purple_heart_100)
    ) {
        Image(
            painter = painterResource(MainRes.images.seat_ic),
            modifier = Modifier.padding(10.dp),
            contentDescription = null
        )
    }
}