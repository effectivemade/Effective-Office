package band.effective.office.elevator.domain.models

import band.effective.office.network.model.ErrorResponse

data class ErrorWithData<out T>(
    val error: ErrorResponse,
    val saveData: T?
)