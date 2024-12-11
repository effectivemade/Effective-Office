package band.effective.office.elevator.navigation

import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

interface Tab {
    val title: StringResource
    val icon: ImageResource
}
