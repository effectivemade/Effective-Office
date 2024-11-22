package band.effective.office.elevator.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import band.effective.office.elevator.AppTheme
import band.effective.office.elevator.EffectiveTheme
import band.effective.office.elevator.ui.root.RootComponent
import band.effective.office.elevator.ui.root.RootContent

@Composable
fun ContentView(component: RootComponent) {
    EffectiveTheme {
        // TODO remove AppTheme when EffectiveTheme is used everywhere
        AppTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                RootContent(component)
            }
        }
    }
}
