package band.effective.office.elevator.ui.booking.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedThemeColors
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun OutlineButtonPurple(
    onClick: () -> Unit,
    icon1: ImageResource,
    icon2: ImageResource,
    title: StringResource,
    rotate: Float
){
    OutlinedButton(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
        onClick = onClick,
        shape = RoundedCornerShape(size = 8.dp),
        border = BorderStroke(1.dp, ExtendedThemeColors.colors.purple_heart_800),
        colors =  ButtonDefaults.buttonColors(backgroundColor = ExtendedThemeColors.colors.whiteColor)
    ){
        Row (verticalAlignment = Alignment.CenterVertically){
            Icon(
                painter = painterResource(icon1),
                contentDescription = null,
                tint = ExtendedThemeColors.colors.purple_heart_800
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(title) ,
                color = ExtendedThemeColors.colors.purple_heart_800,
                style = MaterialTheme.typography.body2
            )
            Icon(
                modifier = Modifier.padding(start = 8.dp).size(24.dp).rotate(rotate),
                painter =  painterResource(icon2),
                contentDescription = null,
                tint = ExtendedThemeColors.colors.purple_heart_800
            )
        }
    }
}