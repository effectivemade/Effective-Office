package band.effective.office.elevator.ui.booking.models

data class WorkSpaceUI(
    val workSpaceId: String,
    val workSpaceName: String,
    val workSpaceType: WorkSpaceType,
    val zoneName: String
)

enum class WorkSpaceType( val type: String){
    MEETING_ROOM("meeting"),
    WORK_PLACE("regular")
}

fun List<WorkSpaceUI>.mapWorkspaceToZone() =
    map {
        WorkspaceZoneUI (
            name = it.zoneName,
            isSelected = false
        )
    }