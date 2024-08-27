package band.effective.office.tv.screen.supernova.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tv.screen.supernova.model.SupernovaUserUi

@Composable
fun TopSupernova(users: List<List<SupernovaUserUi>>) {
    Row(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        users.forEach { list: List<SupernovaUserUi> ->
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                list.forEach { item: SupernovaUserUi ->
                    SupernovaItem(user = item)
                }
            }
        }
    }
}