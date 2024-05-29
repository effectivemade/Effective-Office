package band.effective.office.tablet.network.repository.impl

import band.effective.office.network.api.Api
import band.effective.office.network.dto.UserDTO
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.ErrorWithData
import band.effective.office.tablet.domain.model.Organizer
import band.effective.office.tablet.network.repository.OrganizerRepository
import band.effective.office.tablet.utils.Converter.toOrganizer
import band.effective.office.tablet.utils.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

class OrganizerRepositoryImpl(private val api: Api) : OrganizerRepository {
    /**Buffer for organization list*/
    private var orgList: MutableStateFlow<Either<ErrorWithData<List<Organizer>>, List<Organizer>>> =
        MutableStateFlow(
            Either.Error(
                ErrorWithData<List<Organizer>>(
                    ErrorResponse(0, ""), null
                )
            )
        )

    private suspend fun loadOrganizersList(): Either<ErrorWithData<List<Organizer>>, List<Organizer>> =
        with(orgList.value) {
            orgList.update {
                api.getUsers(tag = "employee").convert(this)
            }
            orgList.value
        }

    override suspend fun getOrganizersList(): Either<ErrorWithData<List<Organizer>>, List<Organizer>> =
        with(orgList.value) {
            // NOTE if buffer is empty or contain error get data from api, else get data from buffer
            if (this is Either.Error) {
                val response = loadOrganizersList()
                orgList.update { response }
                response
            } else this
        }


    override fun subscribeOnUpdates(scope: CoroutineScope): Flow<Either<ErrorWithData<List<Organizer>>, List<Organizer>>> =
        flow {
            emit(loadOrganizersList())
        }
    /** Map DTO to domain model
     * @param oldValue past save value*/
    private fun Either<ErrorResponse, List<UserDTO>>.convert(oldValue: Either<ErrorWithData<List<Organizer>>, List<Organizer>>) =
        map(errorMapper = { error ->
            ErrorWithData(
                error = error, saveData = when (oldValue) {
                    is Either.Error -> oldValue.error.saveData
                    is Either.Success -> oldValue.data
                }
            )
        },
            successMapper = { user ->
                user.filter { it.tag == "employee" }.map { it.toOrganizer() }
            })
}
