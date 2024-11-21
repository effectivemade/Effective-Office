package band.effective.office.tablet.ui.mainScreen.settingsComponents.uiComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette

@Composable
fun ExitButtonView(
    modifier: Modifier,
    onExitApp: () -> Unit
){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth(0.15f),
            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 10.dp),
            elevation = ButtonDefaults.elevation(0.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.background),
            onClick = {
                onExitApp()
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(MainRes.image.exit),
                        contentDescription = "exit",
                        modifier = Modifier.size(30.dp),
                        tint = LocalCustomColorsPalette.current.tertiaryTextAndIcon
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = MainRes.string.exit,
                        style = MaterialTheme.typography.h5,
                        color = LocalCustomColorsPalette.current.tertiaryTextAndIcon
                    )
                }
            }
        }
    }
}