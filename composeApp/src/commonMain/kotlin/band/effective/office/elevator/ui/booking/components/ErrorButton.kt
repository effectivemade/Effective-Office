package band.effective.office.elevator.ui.booking.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun ErrorButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Button(
        onClick,
        shape = RectangleShape,
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.5.dp
        ),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        modifier = modifier
    ) {
        content()
    }
}