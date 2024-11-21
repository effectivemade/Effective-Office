package band.effective.office.elevator.ui.authorization.components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.getDefaultFont
import band.effective.office.elevator.textGrayColor
@Composable
fun AuthSubTitle(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign?,
) {
    Text(
        text = text,
        modifier = Modifier
            .wrapContentSize()
            .then(modifier),
        style = TextStyle(
            fontSize = 16.sp,
            lineHeight = 20.8.sp,
            fontFamily = getDefaultFont(),
            fontWeight = FontWeight(500),
            color = textGrayColor,
            letterSpacing = 0.1.sp,
        ),
        textAlign = textAlign
    )
}