package band.effective.office.tv.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.grid.TvGridCells
import androidx.tv.foundation.lazy.grid.TvLazyHorizontalGrid
import androidx.tv.foundation.lazy.grid.items
import androidx.tv.foundation.lazy.grid.itemsIndexed

@Composable
fun <T> TopRating(users: List<T>, item: @Composable (modifier: Modifier, T, index: Int) -> Unit) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        TvLazyHorizontalGrid(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f),
            rows = TvGridCells.Fixed(5),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            itemsIndexed(users) { index, user ->
                item(Modifier.width(maxWidth / 2.2f), user, index + 1) }
        }
    }
}
