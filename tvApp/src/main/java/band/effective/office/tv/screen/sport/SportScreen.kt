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
import band.effective.office.tv.screen.sport.components.SportTitle
import band.effective.office.tv.screen.sport.components.TopSportUsers
import band.effective.office.tv.screen.sport.model.SportUserUi
import band.effective.office.tv.ui.theme.EffectiveColor

@Composable
fun SportScreen(
    sportUsers: List<List<SportUserUi>>
) {
    Box(
        modifier = Modifier
            .background(EffectiveColor.backgroundSportColor)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 80.dp, end = 160.dp)
        ) {
            SportTitle()
            Spacer(modifier = Modifier.height(60.dp))
            TopSportUsers(users = sportUsers)
        }
    }
}