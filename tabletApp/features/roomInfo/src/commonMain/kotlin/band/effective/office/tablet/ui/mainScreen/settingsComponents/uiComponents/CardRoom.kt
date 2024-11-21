package band.effective.office.tablet.ui.mainScreen.settingsComponents.uiComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette

@Composable
fun CardRoom(
    modifier: Modifier,
    nameRoom: String,
    currentNameRoom: String
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 25.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                modifier = Modifier.scale(1.4f),
                selected = nameRoom == currentNameRoom,
                onClick = null,
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colors.primary,
                    unselectedColor = LocalCustomColorsPalette.current.secondaryTextAndIcon
                )
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = nameRoom,
                style = MaterialTheme.typography.h4,
                color = LocalCustomColorsPalette.current.primaryTextAndIcon
            )
        }
    }
}