package band.effective.office.elevator.ui.booking.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedColors
import band.effective.office.elevator.MainRes
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun WorkspaceZonesErrorButton(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Icon(
            painterResource(MainRes.images.loading_ic),
            tint = ExtendedColors.purple_heart_500,
            contentDescription = null
        )
        Text(
            text = stringResource(MainRes.strings.something_went_wrong),
            style = MaterialTheme.typography.subtitle1.copy(
                color = ExtendedColors.purple_heart_600,
                fontWeight = FontWeight(400)
            )
        )
    }
}