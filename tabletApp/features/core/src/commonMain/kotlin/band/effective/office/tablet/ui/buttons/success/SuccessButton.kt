package band.effective.office.tablet.ui.buttons.success

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun SuccessButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enable: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier.clip(RoundedCornerShape(100.dp)),
        enabled = enable,
        onClick = { onClick() }
    ) {
        content()
    }
}