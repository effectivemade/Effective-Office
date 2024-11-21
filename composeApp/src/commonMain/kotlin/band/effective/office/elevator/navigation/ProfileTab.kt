package band.effective.office.elevator.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import band.effective.office.elevator.MainRes
import dev.icerock.moko.resources.desc.StringDesc

object ProfileTab : Tab {
    override val title = MainRes.strings.profile
    override val icon = Icons.Default.Person
}
