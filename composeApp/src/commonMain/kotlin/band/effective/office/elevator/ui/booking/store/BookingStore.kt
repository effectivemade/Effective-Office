package band.effective.office.elevator.ui.booking.store

import band.effective.office.elevator.MainRes
import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.domain.models.TypeEndPeriodBooking
import band.effective.office.elevator.ui.booking.models.WorkSpaceType
import band.effective.office.elevator.ui.booking.models.WorkSpaceUI
import band.effective.office.elevator.ui.booking.models.WorkspaceZoneUI
import band.effective.office.elevator.ui.booking.models.WorkspacesList
import band.effective.office.elevator.ui.booking.models.sheetData.SelectedBookingPeriodState
import band.effective.office.elevator.ui.bottomSheets.bookingSheet.bookPeriod.store.BookPeriodStore
import band.effective.office.elevator.ui.models.TypesList
import band.effective.office.elevator.utils.getCurrentDate
import com.arkivanov.mvikotlin.core.store.Store
import com.commandiron.wheel_picker_compose.utils.getCurrentTime
import dev.icerock.moko.resources.StringResource
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

interface BookingStore : Store<BookingStore.Intent, BookingStore.State, BookingStore.Label> {

    sealed interface Intent {
        data object OpenChooseZone : Intent

        data object OpenBookPeriod : Intent

        data class OpenBookAccept(val value: WorkSpaceUI) : Intent


        data class ChangeSelectedWorkSpacesZone(val workspaceZoneUI: List<WorkspaceZoneUI>) : Intent

        data class ShowPlace(val type: String) : Intent

        data class ChangeSelectedType(val selectedType: TypesList) : Intent

        data class ApplyBookingPeriodFromSheet (val selectedState: SelectedBookingPeriodState) : Intent

        data class HandleLabelFromBookingPeriodSheet(val label: BookPeriodStore.Label) : Intent

        data object ReloadWorkspacesList : Intent

        data object ReloadWorkspaceZones : Intent
    }

    data class State(
        val workSpaces: List<WorkSpaceUI>,
        val workSpacesAll: List<WorkSpaceUI>,
        val currentDate: LocalDate,
        val workSpacesType: WorkSpaceType,
        val currentWorkspaceZones: List<WorkspaceZoneUI>,
        val allZonesList: WorkspacesList,
        val selectedStartDate: LocalDate,
        val selectedStartTime: LocalTime,
        val selectedFinishTime: LocalTime,
        val selectedFinishDate: LocalDate,
        val repeatBooking: StringResource,
        val bookingPeriod: BookingPeriod,
        val selectedType: TypesList,
        val selectedSeatName: String,
        val selectedWorkspaceId: String,
        val isErrorLoadingWorkspacesList: Boolean,
        val isLoadingListWorkspaces: Boolean,
        val isErrorLoadingWorkspaceZones: Boolean,
        val isLoadingWorkspaceZones: Boolean,
        val typeOfEnd: TypeEndPeriodBooking,
        val dateOfEndPeriod: LocalDate,
    ) {
        companion object {
            val initState = State(
                workSpaces = listOf(),
                workSpacesAll = listOf(),
                currentDate = getCurrentDate(),
                workSpacesType = WorkSpaceType.WORK_PLACE,
                currentWorkspaceZones = listOf(),
                selectedStartDate = getCurrentDate(),
                selectedStartTime = getCurrentTime(),
                selectedFinishTime = getCurrentTime(),
                repeatBooking = MainRes.strings.booking_not_repeat,
                bookingPeriod = BookingPeriod.NoPeriod,
                selectedType = TypesList(
                    name = MainRes.strings.workplace,
                    icon = MainRes.images.table_icon,
                    type = WorkSpaceType.WORK_PLACE
                ),
                selectedSeatName = "",
                selectedFinishDate = getCurrentDate(),
                selectedWorkspaceId = "",
                isLoadingListWorkspaces = true,
                isLoadingWorkspaceZones = false,
                isErrorLoadingWorkspaceZones = false,
                isErrorLoadingWorkspacesList = false,
                typeOfEnd = TypeEndPeriodBooking.CountRepeat(1),
                dateOfEndPeriod = getCurrentDate(),
                allZonesList = WorkspacesList(workspaces = mapOf()),
            )
        }
    }

    sealed interface Label {
        data object OpenChooseZone : Label

        data object OpenBookPeriod : Label

        data object OpenBookAccept : Label

        data class ShowToast(val message: String) : Label
    }
}