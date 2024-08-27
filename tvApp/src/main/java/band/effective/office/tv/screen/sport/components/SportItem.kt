package band.effective.office.tv.screen.sport.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tv.R
import band.effective.office.tv.screen.sport.model.SportUserUi
import band.effective.office.tv.ui.theme.EffectiveColor
import band.effective.office.tv.utils.getCorrectDeclension
import coil.compose.AsyncImage
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
fun SportItem(
    user: SportUserUi
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(350.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = user.photo,
                    contentDescription = null,
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.sport_logo),
                    error = painterResource(id = R.drawable.sport_logo)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = user.name,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = EffectiveColor.fontEventStoryColor,
                    textAlign = TextAlign.Start,
                    maxLines = 1
                )
            }
            Text(
                text = "${user.totalSeconds.toDuration(DurationUnit.SECONDS).inWholeHours.toInt()} "
                        + getCorrectDeclension(
                    number = user.totalSeconds.toDuration(DurationUnit.SECONDS).inWholeHours.toInt(),
                    nominativeCase = stringResource(id = R.string.hour_nominative),
                    genitive = stringResource(id = R.string.hour_genitive),
                    genitivePlural = stringResource(id = R.string.hour_plural)
                ),
                fontFamily = FontFamily(Font(R.font.druktextwidelcg_medium)),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = EffectiveColor.fontEventStoryColor,
                textAlign = TextAlign.Right,
                maxLines = 1
            )
        }
    }

}