package band.effective.office.elevator.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.components.bottomSheet.MultiBottomSheetController

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MultiBottomSheet(
    multiBottomSheetController: MultiBottomSheetController,
    mainContent: @Composable () -> Unit
) {
    var currentSheetState by remember {
        mutableStateOf(
            multiBottomSheetController.getCurrentSheetState()
        )
    }

    LaunchedEffect(Unit) {
        multiBottomSheetController.currentState.collect {
            currentSheetState = it
        }
    }

    ModalBottomSheetLayout(
        sheetState = currentSheetState,
        sheetContent = multiBottomSheetController.getCurrentSheetContent(),
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    ) {
        mainContent()
    }

}

