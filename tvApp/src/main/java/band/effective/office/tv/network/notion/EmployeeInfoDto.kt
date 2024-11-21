package band.effective.office.tv.network.notion

class EmployeeInfoDto(
    val id: String,
    val mail: String,
    val firstName: String?,
    val startDate: String?,
    val nextBirthdayDate: String?,
    val photoUrl: String? = "",
)