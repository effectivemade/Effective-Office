package band.effective.office.elevator.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedThemeColors

@Composable
fun ElevatorUpButton(
    buttonText: String,
    enable: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = ExtendedThemeColors.colors.purple_heart_800,
            contentColor = MaterialTheme.colors.background
        ),
        shape = RoundedCornerShape(48.dp),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 5.dp),
        enabled = enable
    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.button,
        )
    }
}