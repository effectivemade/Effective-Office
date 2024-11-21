package band.effective.office.tablet.ui.mainScreen.mainScreen.uiComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import band.effective.office.tablet.ui.loader.Loader

@Composable
fun LoadMainScreen() {
    Box(
        modifier = Modifier.background(color = MaterialTheme.colors.background).fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Loader()
    }
}