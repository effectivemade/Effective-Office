package band.effective.office.elevator.components.bottomSheet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import band.effective.office.elevator.utils.Stack
import band.effective.office.elevator.utils.pop
import band.effective.office.elevator.utils.stackOf

@Composable
fun rememberMultiBottomSheetController(
    sheetContents: Map<String, BottomSheetItem>,
    sheetStack: Stack<String> = stackOf()
): MultiBottomSheetController {

    return rememberSaveable(sheetStack,
        saver = listSaver(
            save = { listOf(sheetStack) },
            restore = {
                MultiBottomSheetController(
                    sheetStack = it[0],
                    sheetContents = sheetContents
                )
            }
        )
    ) {
        MultiBottomSheetController(
            sheetStack = sheetStack,
            sheetContents = sheetContents
        )
    }
}