package band.effective.office.shareddesk

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import band.effective.office.shareddesk.di.appModule
import band.effective.office.shareddesk.interactivemap.presentation.OfficeMap
import band.effective.office.shareddesk.theme.AppTheme
import org.koin.compose.KoinApplication

@Composable
internal fun App() = AppTheme {
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing),
        ) {
            KoinApplication(application = {
                modules(appModule())
            }) {
                OfficeMap()
            }
        }
    }
}
