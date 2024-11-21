package band.effective.office.elevator.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun InfoAboutUserUIComponent(userName:String?, post:String?){
    Column{
        userName?.let{
            Text(
                text = it,
                style = MaterialTheme.typography.subtitle1,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        post?.let{
            Text(
                text = it,
                style = MaterialTheme.typography.subtitle1,
                color = Color.Black
            )
        }
    }
}