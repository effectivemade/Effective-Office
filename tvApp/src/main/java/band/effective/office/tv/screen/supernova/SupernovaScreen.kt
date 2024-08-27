package band.effective.office.tv.screen.supernova

import band.effective.office.tv.screen.supernova.components.TopSupernova
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
import band.effective.office.tv.screen.supernova.components.SupernovaTitle
import band.effective.office.tv.ui.theme.EffectiveColor

@Composable
fun SupernovaScreen(
    supernovaUsers: List<List<SupernovaUserUi>>
) {
    Box(
        modifier = Modifier
            .background(EffectiveColor.backgroundSupernova)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 80.dp, end = 160.dp)
        ) {
            SupernovaTitle()
            Spacer(modifier = Modifier.height(60.dp))
            TopSupernova(users = supernovaUsers)
        }
    }
}