package band.effective.office.elevator.domain.validator

import dev.icerock.moko.resources.StringResource

interface ExtendedUserInfoValidator {

    fun checkPhone(phoneNumber: String): Result
    fun checkName(name: String): Result
    fun checkPost(post: String): Result
    fun checkTelegramNick(telegram: String): Result


    sealed interface Result {

        data object Valid : Result
        data class Invalid(val message: StringResource) : Result
    }
}
