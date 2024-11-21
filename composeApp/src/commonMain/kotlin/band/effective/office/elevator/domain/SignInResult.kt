package band.effective.office.elevator.domain

import band.effective.office.elevator.domain.models.GoogleAccount

interface SignInResultCallback {
    fun onSuccess(googleAccount: GoogleAccount)
    fun onFailure(message: String)
}
