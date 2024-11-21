package band.effective.office.elevator.ui.booking.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ui.booking.components.modals.WorkingZones

@Composable
fun <T> HorizontalGirdItems(
     countItemsInRow: Int,
     modifier: Modifier = Modifier,
     listItems: List<T>,
     verticalPaddingContent: Dp = 0.dp,
     horizontalPaddingContent: Dp = 0.dp,
     content: @Composable RowScope.(T, Int, Int) -> Unit
) {
    var currentIndex = 0
    val list: MutableList<List<T>> = mutableListOf()

    while (currentIndex < listItems.size) {
        val endIndex =
            if (currentIndex + countItemsInRow > listItems.size)
                listItems.size
            else currentIndex + countItemsInRow

        list.add(
            listItems.subList(currentIndex, endIndex)
        )

        currentIndex += countItemsInRow
    }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(verticalPaddingContent),
    ) {
        itemsIndexed(list){columnIndex, row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(horizontalPaddingContent)
            ) {
                row.forEachIndexed{ rowIndex, item ->
                    content(item, columnIndex, rowIndex)
                }
            }
        }
    }
}