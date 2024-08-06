package band.effective.office.tv.screen.sport.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tv.screen.sport.model.SportUserUi

@Composable
fun TopSportUsers(users: List<List<SportUserUi>>) {
    val usersList = remember { users }

    Row(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        usersList.forEach { list: List<SportUserUi> ->
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)

            ) {
                list.forEach { item: SportUserUi ->
                    SportItem(user = item)
                }

            }
        }
    }
}