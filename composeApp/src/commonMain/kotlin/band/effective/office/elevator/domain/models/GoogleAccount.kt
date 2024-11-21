package band.effective.office.elevator.domain.models

data class GoogleAccount(
    val email: String,
    val name: String,
    val photoUrl: String?,
    val idToken: String?
)

