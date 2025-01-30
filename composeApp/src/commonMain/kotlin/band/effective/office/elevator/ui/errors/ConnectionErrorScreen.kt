package band.effective.office.elevator.ui.errors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.EffectiveTheme
import band.effective.office.elevator.MainRes
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ConnectionErrorScreen(
    modifier: Modifier = Modifier,
    updateClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(EffectiveTheme.colors.background.primary)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = painterResource(MainRes.images.ic_wifi),
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = Color.Unspecified,
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Text(
                text = stringResource(MainRes.strings.connection_error_title),
                color = EffectiveTheme.colors.text.primary,
                style = EffectiveTheme.typography.mMedium,
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = stringResource(MainRes.strings.connection_error_description),
                color = EffectiveTheme.colors.text.secondary,
                style = EffectiveTheme.typography.sMedium,
            )
            Spacer(modifier = Modifier.padding(22.dp))
            TextButton(
                onClick = updateClick,
                content = {
                    Text(
                        text = stringResource(MainRes.strings.try_again),
                        color = EffectiveTheme.colors.text.accent,
                        style = EffectiveTheme.typography.sMedium,
                    )
                }
            )
        }
    }
}
