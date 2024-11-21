package band.effective.office.elevator.ui.employee.aboutEmployee.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedThemeColors
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun ContactUserUIComponent(image:ImageResource, value: String?, modifier: Modifier){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
       Icon(
           painter = painterResource(image),
           contentDescription = null,
           modifier = Modifier.size(16.dp),
           tint = ExtendedThemeColors.colors.purple_heart_800
       )
        value?.let{
            Text(
                text = it,
                style = MaterialTheme.typography.caption,
                color = ExtendedThemeColors.colors.purple_heart_800,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}