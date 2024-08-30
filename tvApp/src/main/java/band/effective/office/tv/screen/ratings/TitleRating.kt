package band.effective.office.tv.screen.ratings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tv.R
import band.effective.office.tv.ui.theme.EffectiveColor

@Composable
fun TitleRating(
    modifier: Modifier = Modifier,
    imagePath: Int,
    stringPath: Int
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            modifier = modifier.size(32.dp),
            painter = painterResource(id = imagePath),
            contentDescription = "sport_icon"
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = stringResource(id = stringPath),
            fontFamily = FontFamily(Font(R.font.roboto_regular)),
            fontSize = 14.sp,
            color = EffectiveColor.fontEventStoryColor
        )
    }
}