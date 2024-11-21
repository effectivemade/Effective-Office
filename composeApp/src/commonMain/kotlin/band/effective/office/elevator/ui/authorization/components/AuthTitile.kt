package band.effective.office.elevator.ui.authorization.components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import band.effective.office.elevator.ExtendedThemeColors

@Composable
fun AuthTitle(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign?,
) {
    Text(
        text = text,
        modifier = Modifier
            .wrapContentSize()
            .then(modifier),
        style = MaterialTheme.typography.h5,
        color = ExtendedThemeColors.colors.purple_heart_800,
        textAlign = textAlign,
    )
}