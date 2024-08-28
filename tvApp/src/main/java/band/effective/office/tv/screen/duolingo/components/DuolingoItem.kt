package band.effective.office.tv.screen.duolingo.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tv.R
import band.effective.office.tv.screen.duolingo.model.DuolingoUserUI
import band.effective.office.tv.screen.eventStory.KeySortDuolingoUser
import band.effective.office.tv.ui.theme.EffectiveColor
import coil.compose.AsyncImage

@Composable
fun DuolingoItem(
    modifier: Modifier = Modifier,
    user: DuolingoUserUI,
    indicatorUsers: String,
    place: Int,
    keySort: KeySortDuolingoUser
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(width = 50.dp, height = 45.dp)
                ) {
                    AsyncImage(
                        model = user.photo,
                        contentDescription = null,
                        modifier = Modifier
                            .size(45.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.duolingo_logo),
                        error = painterResource(id = R.drawable.duolingo_logo)
                    )
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = place.toString(),
                                fontFamily = FontFamily(Font(R.font.druktextwidelcg_medium)),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = when (keySort) {
                                    KeySortDuolingoUser.Xp -> EffectiveColor.duolingo
                                    KeySortDuolingoUser.Streak -> EffectiveColor.dayStreakDuolingo
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(15.dp))
                Column {
                    Text(
                        text = user.username,
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = EffectiveColor.fontEventStoryColor,
                        textAlign = TextAlign.Start,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        user.countryLang.forEach { flag ->
                            Flag(modifier = Modifier
                                .size(width = 15.dp, height = 12.5.dp),
                                drawableFlagId = flag.drawableId
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                        }
                    }
                }
            }
        }
        Text(
            text = indicatorUsers,
            fontFamily = FontFamily(Font(R.font.druktextwidelcg_medium)),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = EffectiveColor.fontEventStoryColor,
            textAlign = TextAlign.Right,
            maxLines = 1
        )
    }
}