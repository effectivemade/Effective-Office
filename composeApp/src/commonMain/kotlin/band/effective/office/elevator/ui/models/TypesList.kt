package band.effective.office.elevator.ui.models

import band.effective.office.elevator.ui.booking.models.WorkSpaceType
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

data class TypesList(
    val name: StringResource,
    val icon: ImageResource,
    val type: WorkSpaceType = WorkSpaceType.WORK_PLACE
)
