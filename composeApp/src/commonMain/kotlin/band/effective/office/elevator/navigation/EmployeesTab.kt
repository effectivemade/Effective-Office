package band.effective.office.elevator.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.ui.graphics.vector.ImageVector
import band.effective.office.elevator.MainRes
import dev.icerock.moko.resources.StringResource

object EmployeesTab: Tab {
    override val title: StringResource
        get() = MainRes.strings.employees
    override val icon: ImageVector
        get() = Icons.Default.Group
}