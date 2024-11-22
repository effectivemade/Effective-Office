package band.effective.office.elevator.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.EffectiveTheme

@Composable
fun UserScreen(
    modifier: Modifier = Modifier,
    userName: String?,
    post: String?,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        userName?.let {
            Text(
                text = it,
                style = EffectiveTheme.typography.mMedium,
                color = EffectiveTheme.colors.text.primary,
                fontWeight = FontWeight(500),
                textAlign = TextAlign.Center,
            )
        }
        post?.let {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = it,
                style = EffectiveTheme.typography.sMedium,
                color = EffectiveTheme.colors.text.secondary,
                textAlign = TextAlign.Center,
            )
        }
    }
}
