package band.effective.office.elevator.components.buttons

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import band.effective.office.elevator.EffectiveTheme

@Composable
fun ToggleButton(
    modifier: Modifier = Modifier,
) {
    var checked by remember { mutableStateOf(true) }

    Switch(
        modifier = modifier,
        checked = checked,
        onCheckedChange = {
            checked = it
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = EffectiveTheme.colors.button.toggleNormal,
            checkedTrackColor = EffectiveTheme.colors.button.toggleOff,
            uncheckedThumbColor = EffectiveTheme.colors.button.toggleNormal,
            uncheckedTrackColor = EffectiveTheme.colors.button.toggleOn,
            checkedBorderColor = EffectiveTheme.colors.button.toggleOff,
            uncheckedBorderColor = EffectiveTheme.colors.button.toggleOn,
        )
    )
}
