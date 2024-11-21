package band.effective.office.elevator.data.repository

import band.effective.office.elevator.data.ApiResponse
import band.effective.office.elevator.data.NetworkClient
import band.effective.office.elevator.domain.GoogleSignIn
import band.effective.office.elevator.domain.repository.OfficeElevatorRepository
import io.ktor.client.request.parameter
import io.ktor.client.request.url

class OfficeElevatorRepositoryImpl(
    private val client: NetworkClient,
    private val signInClient: GoogleSignIn
) : OfficeElevatorRepository {

    override suspend fun call(): ApiResponse<Unit, Error> {
        return when (val result = signInClient.retrieveAuthorizedUser()) {
            is ApiResponse.Error.HttpError -> ApiResponse.Error.HttpError(
                result.code,
                result.errorBody
            )

            ApiResponse.Error.NetworkError -> ApiResponse.Error.NetworkError
            ApiResponse.Error.SerializationError -> ApiResponse.Error.SerializationError
            ApiResponse.Error.UnknownError -> ApiResponse.Error.UnknownError
            is ApiResponse.Success -> {
                client.call<Unit, Error> {
                    url("elevate")
                    parameter("key", result.body.idToken)
                }

            }
        }
    }
}
