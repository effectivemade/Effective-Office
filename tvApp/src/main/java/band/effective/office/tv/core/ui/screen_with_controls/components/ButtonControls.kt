package band.effective.office.tv.core.ui.screen_with_controls.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource


@Composable
fun ButtonControls(
    isFocus: Boolean,
    idActiveIcon: Int,
    idInactiveIcon: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Image(
        painter = painterResource(
            if (isFocus) idActiveIcon
            else idInactiveIcon
        ),
        contentDescription = null,
        modifier = modifier
            .clickable { onClick() }
    )
}