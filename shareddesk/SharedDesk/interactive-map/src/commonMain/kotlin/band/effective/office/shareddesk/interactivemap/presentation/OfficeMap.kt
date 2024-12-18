package band.effective.office.shareddesk.interactivemap.presentation

import androidx.compose.runtime.Composable
import band.effective.office.shareddesk.interactivemap.utils.Platform
import band.effective.office.shareddesk.interactivemap.utils.getPlatform

@Composable
fun OfficeMap() {
    when (getPlatform()) {
        Platform.MOBILE -> OfficeMapViewer()
        Platform.PC -> AdminOfficeMap()
    }
}
