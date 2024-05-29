package band.effective.office.tvServer.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface Event {
    @Serializable
    data class Birthday(
        val name: String,
        val photoUrl: String
    ) : Event

    @Serializable
    data class AnnualAnniversary(
        val employeeName: String,
        val employeePhotoUrl: String,
        val yearsInCompany: Int
    ) : Event

    @Serializable
    data class MonthAnniversary(
        val employeeName: String,
        val employeePhotoUrl: String,
        val yearsInCompany: Int,
        val monthsInCompany: Int
    ) : Event

    @Serializable
    data class NewEmployee(
        val employeeName: String,
        val employeePhotoUrl: String,
    ) : Event
}
