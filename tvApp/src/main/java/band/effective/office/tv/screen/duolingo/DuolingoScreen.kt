package band.effective.office.tv.screen.duolingo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import band.effective.office.tv.R
import band.effective.office.tv.screen.ratings.TitleRating
import band.effective.office.tv.screen.ratings.TopRating
import band.effective.office.tv.screen.duolingo.components.DuolingoItem
import band.effective.office.tv.screen.duolingo.model.DuolingoUserUI
import band.effective.office.tv.screen.eventStory.KeySortDuolingoUser
import band.effective.office.tv.ui.theme.EffectiveColor
import band.effective.office.tv.utils.getCorrectDeclension

@Composable
fun DuolingoScreen(
    keySort: KeySortDuolingoUser,
    duolingoUser: List<DuolingoUserUI>
) {
    Box(
        modifier = Modifier
            .background(EffectiveColor.duolingo)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 60.dp, end = 120.dp)
        ) {
            TitleRating(
                modifier = Modifier.clip(CircleShape),
                imagePath = R.drawable.duolingo_logo,
                stringPath = R.string.duolingo_title
            )
            Spacer(modifier = Modifier.height(30.dp))
            TopRating(users = duolingoUser) { modifier, duolingoUserUi, index ->
                val result = when (keySort) {
                    KeySortDuolingoUser.Streak -> {
                        "${duolingoUserUi.streakDay} " +
                                getCorrectDeclension(
                                    number = duolingoUserUi.streakDay,
                                    nominativeCase = stringResource(id = R.string.day_nominative),
                                    genitive = stringResource(id = R.string.day_genitive),
                                    genitivePlural = stringResource(id = R.string.day_plural)
                                )
                    }
                    KeySortDuolingoUser.Xp -> "${duolingoUserUi.totalXp} " + stringResource(id = R.string.xp)
                }
                DuolingoItem(
                    modifier = modifier,
                    user = duolingoUserUi,
                    indicatorUsers = result,
                    place = index,
                    keySort = keySort
                )
            }
        }
    }
}