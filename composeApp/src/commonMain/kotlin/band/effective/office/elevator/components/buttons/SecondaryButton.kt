package band.effective.office.elevator.components.buttons

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.EffectiveTheme

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(40.dp),
        contentPadding = PaddingValues(horizontal = 40.dp, vertical = 12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPressed) {
                EffectiveTheme.colors.button.secondaryPress
            } else {
                EffectiveTheme.colors.button.secondaryNormal
            }
        ),
        interactionSource = interactionSource,
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = EffectiveTheme.typography.mMedium,
            color = if (isPressed) {
                EffectiveTheme.colors.text.secondary
            } else {
                EffectiveTheme.colors.text.accent
            }
        )
    }
}
