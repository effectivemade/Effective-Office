package band.effective.office.tv.screen.supernova.components

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
fun SupernovaTitle() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            modifier = Modifier.size(45.dp),
            painter = painterResource(id = R.drawable.supernova),
            contentDescription = "supernova_icon"
        )
        Spacer(modifier = Modifier.width(30.dp))
        Text(
            text = stringResource(id = R.string.supernova_title),
            fontFamily = FontFamily(Font(R.font.roboto_regular)),
            fontSize = 17.sp,
            color = EffectiveColor.fontEventStoryColor
        )
    }
}