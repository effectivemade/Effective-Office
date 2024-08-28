package band.effective.office.tv.screen.supernova

import band.effective.office.tv.screen.supernova.model.SupernovaUserUi
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
import band.effective.office.tv.ui.theme.EffectiveColor

@Composable
fun SupernovaScreen(
    supernovaUsers: List<SupernovaUserUi>
) {
    Box(
        modifier = Modifier
            .background(EffectiveColor.supernova)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 60.dp, end = 120.dp)
        ) {
            TitleRating(
                imagePath = R.drawable.supernova,
                stringPath = R.string.supernova_title
            )
            Spacer(modifier = Modifier.height(30.dp))
            TopRating(users = supernovaUsers) { modifier, supernovaUi, _ ->
                SupernovaItem(
                    modifier = modifier,
                    user = supernovaUi
                )
            }
        }
    }
}