package band.effective.office.tv.screen.supernova

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
    modifier: Modifier,
    user: SupernovaUserUi
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = user.photoUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.supernova),
                error = painterResource(id = R.drawable.supernova)
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${user.score}",
                fontFamily = FontFamily(Font(R.font.druktextwidelcg_medium)),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = EffectiveColor.fontEventStoryColor,
                textAlign = TextAlign.Right
            )
            Spacer(modifier = Modifier.width(2.dp))
            Image(
                modifier = Modifier.size(15.dp),
                painter = painterResource(id = R.drawable.currency),
                contentDescription = null
            )
        }
    }
}