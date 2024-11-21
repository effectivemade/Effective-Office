package band.effective.office.elevator.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.ui.graphics.vector.ImageVector
import band.effective.office.elevator.MainRes
import dev.icerock.moko.resources.StringResource

object BookingTab: Tab {
    override val title: StringResource
        get() = MainRes.strings.booking
    override val icon: ImageVector
        get() = Icons.Default.DateRange
}