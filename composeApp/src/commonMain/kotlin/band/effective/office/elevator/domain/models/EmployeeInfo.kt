package band.effective.office.elevator.domain.models

import band.effective.office.network.dto.UserDTO
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class EmployeeInfo(
    val id: String,
    val name: String,
    val post: String,
    val state: String,
    val logoUrl: String,
    val phoneNum: String,
    val eMail: String,
    val telegramProfile: String
) : Parcelable{
    companion object {
        val defaultEmployee =
            EmployeeInfo(
                id = "",
                name = "",
                post = "",
                state = "",
                logoUrl  = "",
                phoneNum = "",
                eMail = "",
                telegramProfile = ""
            )
    }
}

fun UserDTO.toEmployeeInfo() =
    EmployeeInfo(
        id = id,
        name = fullName,
        post = role,
        state = "",
        logoUrl = avatarUrl,
        phoneNum = integrations?.find { it.name == "phoneNumber" }?.value?:"None",
        eMail = email,
        telegramProfile = integrations?.find { it.name == "telegram" }?.value?:"None",
    )