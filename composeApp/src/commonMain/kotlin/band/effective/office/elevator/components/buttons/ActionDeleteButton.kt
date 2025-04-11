package band.effective.office.elevator.components.buttons

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.EffectiveTheme
import band.effective.office.elevator.MainRes
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ActionDeleteButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPressed) {
                EffectiveTheme.colors.button.actionDeleteNormal
            } else {
                EffectiveTheme.colors.button.actionDeleteNormal
            }
        ),
        interactionSource = interactionSource,
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(MainRes.images.ic_delete),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = EffectiveTheme.colors.icon.primaryEvent,
            )
            Text(
                text = stringResource(MainRes.strings.delete),
                textAlign = TextAlign.Center,
                style = EffectiveTheme.typography.xsMedium,
                color = EffectiveTheme.colors.text.primaryEvent,
            )
        }
    }
}
