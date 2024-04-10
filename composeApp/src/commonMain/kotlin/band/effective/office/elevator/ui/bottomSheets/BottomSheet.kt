package band.effective.office.elevator.ui.bottomSheets

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.StateFlow

interface BottomSheet {
    @Composable
    fun SheetContent()
    @Composable
    fun content()
}