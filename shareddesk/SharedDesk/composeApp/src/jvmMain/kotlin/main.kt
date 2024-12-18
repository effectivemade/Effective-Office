import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import band.effective.office.shareddesk.App

fun main() = application {
    Window(
        title = "SharedDesk",
        state = rememberWindowState(width = 900.dp, height = 1300.dp),
        onCloseRequest = ::exitApplication,
    ) {
        App()
    }
}

@Preview
@Composable
fun AppPreview() {
    App()
}