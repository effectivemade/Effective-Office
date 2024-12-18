package band.effective.office.shareddesk.interactivemap.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntSize

@Composable
actual fun getContainerSize(): IntSize {
    return IntSize(
        width = LocalConfiguration.current.screenWidthDp,
        height = LocalConfiguration.current.screenHeightDp,
    )
}