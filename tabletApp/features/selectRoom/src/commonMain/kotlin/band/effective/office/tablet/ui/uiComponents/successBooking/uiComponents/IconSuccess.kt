package band.effective.office.tablet.ui.uiComponents.successBooking.uiComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.selectRoom.MainRes
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette

@Composable
fun IconSuccess() {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(MainRes.image.check),
            contentDescription = "Check",
            modifier = Modifier.size(60.dp),
            tint = LocalCustomColorsPalette.current.onSuccess
        )
    }
}