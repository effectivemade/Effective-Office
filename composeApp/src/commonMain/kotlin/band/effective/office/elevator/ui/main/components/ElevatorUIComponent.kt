package band.effective.office.elevator.ui.main.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.ElevatorUpButton
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ElevatorUIComponent(
    enable: Boolean,
    onClickCallElevator: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = stringResource(MainRes.strings.elevator),
            style = MaterialTheme.typography.subtitle1,
            color = ExtendedThemeColors.colors.blackColor,
            fontWeight = FontWeight(500)
        )
        Spacer(Modifier.weight(.1f))
        ActionCall(enable = enable, onClickCallElevator = onClickCallElevator)
    }
}

@Composable
private fun ActionCall(enable: Boolean, onClickCallElevator: () -> Unit) {
    ElevatorUpButton(
        buttonText = stringResource(MainRes.strings.elevator_button),
        onClick = onClickCallElevator,
        enable = enable
    )
}

