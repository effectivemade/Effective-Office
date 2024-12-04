package band.effective.office.elevator.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.EffectiveTheme

@Composable
fun EffectiveButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    buttonText: String,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
    ) {
        Text(
            text = buttonText,
            style = EffectiveTheme.typography.mMedium,
            color = EffectiveTheme.colors.text.primaryEvent,
        )
    }
}
