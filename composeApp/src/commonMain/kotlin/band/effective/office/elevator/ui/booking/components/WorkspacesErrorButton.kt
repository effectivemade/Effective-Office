package band.effective.office.elevator.ui.booking.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.MainRes
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun WorkspacesErrorButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ErrorButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                8.dp,
                alignment = Alignment.CenterHorizontally
            ),
        ) {
            Icon(
                painterResource(MainRes.images.loading_ic),
                tint = MaterialTheme.colors.primary,
                contentDescription = null
            )
            Text(
                text = stringResource(MainRes.strings.something_went_wrong),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center
            )
        }
    }
}