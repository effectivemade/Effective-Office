package band.effective.office.elevator.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import band.effective.office.elevator.EffectiveTheme

@Composable
fun TitlePage(
    modifier: Modifier = Modifier,
    title: String,
    textAlign: TextAlign = TextAlign.Center,
) {
    Row(modifier = modifier) {
        Text(
            text = title,
            style = EffectiveTheme.typography.mMedium,
            color = EffectiveTheme.colors.text.primary,
            textAlign = textAlign,
        )
    }
}
