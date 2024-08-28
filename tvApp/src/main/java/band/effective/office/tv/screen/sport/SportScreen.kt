package band.effective.office.tv.screen.sport

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tv.R
import band.effective.office.tv.screen.components.TitleRating
import band.effective.office.tv.screen.components.TopRating
import band.effective.office.tv.screen.sport.model.SportUserUi
import band.effective.office.tv.ui.theme.EffectiveColor

@Composable
fun SportScreen(
    sportUsers: List<SportUserUi>
) {
    Box(
        modifier = Modifier
            .background(EffectiveColor.sport)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 60.dp, end = 120.dp)
        ) {
            TitleRating(
                imagePath = R.drawable.sport_logo,
                stringPath = R.string.sport_title
            )
            Spacer(modifier = Modifier.height(30.dp))
            TopRating(users = sportUsers) { modifier, sportUserUi, _ ->
                SportItem(
                    modifier = modifier,
                    user = sportUserUi
                )
            }
        }
    }
}