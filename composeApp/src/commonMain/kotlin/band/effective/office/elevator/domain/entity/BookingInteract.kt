package band.effective.office.elevator.domain.entity

import band.effective.office.elevator.domain.models.BookingInfo
import band.effective.office.elevator.domain.models.CreatingBookModel
import band.effective.office.elevator.domain.models.ErrorWithData
import band.effective.office.elevator.domain.repository.BookingRepository
import band.effective.office.elevator.domain.useCase.ChangeBookingUseCase
import band.effective.office.elevator.domain.useCase.CreateBookingUseCase
import band.effective.office.elevator.domain.useCase.GetBookingsUseCase
import band.effective.office.elevator.domain.useCase.WorkspacesUseCase
import band.effective.office.elevator.ui.booking.models.WorkSpaceUI
import band.effective.office.elevator.ui.booking.models.WorkspaceZoneUI
import band.effective.office.elevator.ui.employee.aboutEmployee.models.BookingsFilter
import band.effective.office.elevator.ui.models.ReservedSeat
import band.effective.office.network.model.Either
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime

/*TODO: Does ui models be in domain module? */

class BookingInteract(
    private val getBookingsUseCase: GetBookingsUseCase,
    private val changeBookingUseCase: ChangeBookingUseCase,
    private val createBookingUseCase: CreateBookingUseCase,
    private val workspaceUseCase: WorkspacesUseCase,
    private val repository: BookingRepository // todo replace this
) {
    suspend fun getForUser(
        ownerId:String? = null,
        beginDateTime: LocalDateTime,
        endDateTime: LocalDateTime,
        bookingsFilter: BookingsFilter
    ): Flow<Either<ErrorWithData<List<ReservedSeat>>, List<ReservedSeat>>> =
        getBookingsUseCase.getBookingsForUser(
            ownerId = ownerId,
            beginDateTime = beginDateTime,
            endDateTime = endDateTime,
            bookingsFilter = bookingsFilter
        )

    suspend fun change(bookingInfo: BookingInfo) {
        changeBookingUseCase.execute(
            bookingInfo = bookingInfo
        )
    }

    suspend fun create(creatingBookModel: CreatingBookModel) =
        createBookingUseCase.execute(creatingBookModel = creatingBookModel)


    suspend fun getZones() = workspaceUseCase.getAllWorkspaces()


    suspend fun getWorkspaces(
        tag: String,
        freeFrom: LocalDateTime? = null,
        freeUntil: LocalDateTime? = null,
        selectedWorkspacesZone: List<WorkspaceZoneUI>
    ) = workspaceUseCase
            .getWorkSpaces(
                tag = tag,
                freeFrom = freeFrom,
                freeUntil = freeUntil
        ).map { response ->
            when (response) {
                is Either.Success -> {
                    val listWorkSpaces = response.data
                    val filteredWorkspacesList = filterWorkspacesList(
                        workspaces = listWorkSpaces, zones = selectedWorkspacesZone
                    )
                    val sortedWorkspacesList = sortWorkspacesList(filteredWorkspacesList)
                    Either.Success(sortedWorkspacesList)
                }

                is Either.Error -> {
                    Napier.e {"ERROORRR!!!! ${response.error.error}"}
                    Either.Error(response.error)
                }
            }
        }

    // TODO: Переписать с использльзованием id вместо имени для фильтрации
    fun filterWorkspacesList(
        workspaces: List<WorkSpaceUI>,
        zones: List<WorkspaceZoneUI>
    ) : List<WorkSpaceUI> {
        Napier.d{ "Zones: $zones"}
        val selectedZones = zones.filter { it.isSelected }
        return workspaces.filter { workspaces ->
            selectedZones.count { zone -> zone.name == workspaces.zoneName } > 0
        }
    }
    fun sortWorkspacesList(
        workspaces: List<WorkSpaceUI>
    ): List<WorkSpaceUI> {
        return workspaces.sortedWith(
            compareBy({it.zoneName.first()}, {it.workSpaceName.last()})).toMutableList()
    }

    suspend fun deleteBooking(bookingId: String) = repository.deleteBooking(bookingId)
}