@file:OptIn(ExperimentalMaterialApi::class)

package band.effective.office.elevator.components.bottomSheet

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable

class BottomSheetItem @OptIn(ExperimentalMaterialApi::class) constructor(
    val bottomSheetContentState: ModalBottomSheetState,
    val bottomSheetContent: @Composable ColumnScope.() -> Unit
) {
    @OptIn(ExperimentalMaterialApi::class)
    suspend fun hideBottomSheet() = bottomSheetContentState.hide()

    @OptIn(ExperimentalMaterialApi::class)
    suspend fun showBottomSheet() = bottomSheetContentState.show()

    @OptIn(ExperimentalMaterialApi::class)
    fun isVisible() = bottomSheetContentState.isVisible
}
