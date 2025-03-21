package band.effective.office.tv.screen.menu.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import band.effective.office.tv.ui.theme.robotoFontFamily

@Composable
fun MainMenuPlaceHolder(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontFamily = robotoFontFamily(),
        fontSize = 50.sp,
        color = Color.White,
        modifier = modifier
    )
    Spacer(Modifier.height(20.dp))
    Text(
        text = "Скоро",
        fontFamily = robotoFontFamily(),
        fontSize = 25.sp,
        color = Color.White
    )
}