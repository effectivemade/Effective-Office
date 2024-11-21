package band.effective.office.elevator.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import dev.icerock.moko.resources.StringResource

interface Tab {
    val title: StringResource
    val icon: ImageVector
}
