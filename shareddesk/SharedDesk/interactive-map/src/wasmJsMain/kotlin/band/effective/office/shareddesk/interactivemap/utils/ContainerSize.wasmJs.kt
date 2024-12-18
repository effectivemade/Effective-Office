package band.effective.office.shareddesk.interactivemap.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.IntSize

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getContainerSize(): IntSize {
    return LocalWindowInfo.current.containerSize
}