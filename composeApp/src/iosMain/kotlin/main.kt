import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import band.effective.office.elevator.AppTheme
import band.effective.office.elevator.data.database.di.databaseModule
import band.effective.office.elevator.di.appModuleDI
import band.effective.office.elevator.di.iosModuleDI
import band.effective.office.elevator.ui.helper.LocalSafeArea
import band.effective.office.elevator.ui.root.ContentView
import band.effective.office.elevator.ui.root.RootComponent
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController

@Suppress("unused", "FunctionName")
fun MainViewController(
    lifecycle: LifecycleRegistry,
    topSafeArea: Float,
    bottomSafeArea: Float
): UIViewController {
    startKoin {
        modules(appModuleDI + iosModuleDI + databaseModule)
    }

    val rootComponent =
        RootComponent(
            componentContext = DefaultComponentContext(lifecycle = lifecycle),
            storeFactory = DefaultStoreFactory(),
        )

    return ComposeUIViewController {
        val density = LocalDensity.current

        val topSafeAreaDp = with(density) { topSafeArea.toDp() }
        val bottomSafeAreaDp = with(density) { bottomSafeArea.toDp() }
        val safeArea = PaddingValues(top = topSafeAreaDp + 10.dp, bottom = bottomSafeAreaDp)

        // Bind safe area as the value for LocalSafeArea
        CompositionLocalProvider(LocalSafeArea provides safeArea) {
            AppTheme {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize().padding(safeArea)
                ) {
                    ContentView(
                        component = rootComponent,
                    )
                }
            }
        }
    }
}
