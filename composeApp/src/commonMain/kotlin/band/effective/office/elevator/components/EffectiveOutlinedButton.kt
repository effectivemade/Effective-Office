package band.effective.office.elevator.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.MainRes
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun EffectiveOutlinedButton(icon : ImageResource?, text: StringResource?, onClick:()->Unit, modifier: Modifier){
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(size = 8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
        modifier = modifier
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            icon?.let{
                if(icon ==MainRes.images.spb_icon){
                    Image(
                        painter = painterResource(icon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }else{
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = null,
                        tint = MaterialTheme.colors.secondary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            text?.let {
                Text(
                    stringResource(text),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}