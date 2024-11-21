package band.effective.office.tablet.ui.buttons.alert

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AlertButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
){
    Button(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp)).border(
                width = 3.dp,
                shape = RoundedCornerShape(100.dp),
                color = MaterialTheme.colors.onPrimary
            ),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        onClick = { onClick() }
    ){
        content()
    }
}