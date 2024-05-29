package band.effective.office.elevator.ui.booking.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
fun ZonesSelectionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick, modifier = modifier,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(MainRes.images.icon_location),
                tint = ExtendedColors.purple_heart_500,
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(MainRes.strings.select_zones),
                style = MaterialTheme.typography.subtitle1.copy(
                    color = ExtendedColors.purple_heart_600,
                    fontWeight = FontWeight(400)
                )
            )
        }
    }
}