package band.effective.office.tablet.ui.mainScreen.settingsComponents.uiComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.theme.CustomDarkColors
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.h6_button

@Composable
fun ChooseButtonView(
    modifier: Modifier,
    nameRoom: String,
    onSaveData: () -> Unit
){
    val isPressed = remember { mutableStateOf(false) }
    val colorButton = if (isPressed.value)
        LocalCustomColorsPalette.current.pressedPrimaryButton else MaterialTheme.colors.primary

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.4f),
            elevation = ButtonDefaults.elevation(0.dp),
            colors = ButtonDefaults.buttonColors(colorButton),
            shape = RoundedCornerShape(100),
            onClick = {
                isPressed.value = !isPressed.value
                onSaveData()
            }
        ) {
            Text(
                text = MainRes.string.choose_room.format(
                    nameRoom = nameRoom
                ),
                style = MaterialTheme.typography.h6_button,
                color = when (LocalCustomColorsPalette.current) {
                    CustomDarkColors -> LocalCustomColorsPalette.current.primaryTextAndIcon
                    else -> MaterialTheme.colors.background
                }
            )
        }
    }
}