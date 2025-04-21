package band.effective.office.elevator.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.EffectiveTheme

@Composable
fun TimeButton(
    modifier: Modifier = Modifier,
    textTime: String,
    isActive: Boolean,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPressed) {
                EffectiveTheme.colors.button.secondaryPress
            } else {
                EffectiveTheme.colors.button.secondaryNormal
            }
        ),
        border = BorderStroke(
            width = 1.dp, color =
            if (isActive) {
                EffectiveTheme.colors.stroke.accent
            } else Color.Transparent
        ),
        interactionSource = interactionSource,
    ) {
        Text(
            text = textTime,
            textAlign = TextAlign.Left,
            style = EffectiveTheme.typography.mMedium,
            color = if (isPressed) {
                EffectiveTheme.colors.text.secondary
            } else {
                EffectiveTheme.colors.text.accent
            }
        )
        Spacer(modifier = Modifier.padding(end = 100.dp))
    }
}
