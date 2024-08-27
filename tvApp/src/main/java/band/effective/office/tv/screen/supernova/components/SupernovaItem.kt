package band.effective.office.tv.screen.supernova.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tv.R
import band.effective.office.tv.screen.supernova.model.SupernovaUserUi
import band.effective.office.tv.ui.theme.EffectiveColor
import coil.compose.AsyncImage

@Composable
fun SupernovaItem(
    user: SupernovaUserUi
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.width(480.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = user.photoUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.supernova),
                    error = painterResource(id = R.drawable.supernova)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    modifier = Modifier.width(190.dp),
                    text = user.name,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = EffectiveColor.fontEventStoryColor,
                    textAlign = TextAlign.Start,
                    maxLines = 1
                )
            }
            Text(
                text = "${user.score}",
                fontFamily = FontFamily(Font(R.font.druktextwidelcg_medium)),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = EffectiveColor.fontEventStoryColor,
                textAlign = TextAlign.Right
            )
            Image(
                painter = painterResource(id = R.drawable.currency),
                contentDescription = null
            )
        }
    }

}