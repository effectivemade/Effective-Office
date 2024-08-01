package band.effective.office.tv.screen.sport.components

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tv.screen.sport.model.SportUserUi

@Composable
fun TopSportUsers(users: List<SportUserUi>) {
    val usersList: MutableList<List<SportUserUi>> = mutableListOf()
    if (users.size > 5) {
        usersList.add(users.subList(0, 5))
        usersList.add(users.subList(5, users.size))
    }
    else
        usersList.add(users)
    LazyRow(
        modifier = Modifier
            .focusable()
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        userScrollEnabled = true
    ) {
        itemsIndexed(usersList) { _, usersColumn ->
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                itemsIndexed(usersColumn) { _, item: SportUserUi ->
                    SportItem(item)
                }
            }
        }
    }
}