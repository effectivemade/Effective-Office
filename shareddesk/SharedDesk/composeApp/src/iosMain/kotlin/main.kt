import androidx.compose.ui.window.ComposeUIViewController
import band.effective.office.shareddesk.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
