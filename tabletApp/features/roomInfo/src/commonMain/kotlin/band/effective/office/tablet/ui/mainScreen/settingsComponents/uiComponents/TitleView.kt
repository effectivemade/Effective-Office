package band.effective.office.tablet.ui.mainScreen.settingsComponents.uiComponents

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette

@Composable
fun TitleView(modifier: Modifier) {
    Text(
        modifier = modifier,
        text = MainRes.string.rooms,
        style = MaterialTheme.typography.h2,
        color = LocalCustomColorsPalette.current.primaryTextAndIcon
    )
}