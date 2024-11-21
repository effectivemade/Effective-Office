package band.effective.office.tv.screen.duolingo.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun Flag(modifier: Modifier = Modifier, drawableFlagId: Int) {
    Image(
        modifier = modifier,
        painter = painterResource(id = drawableFlagId),
        contentDescription = null
    )
}