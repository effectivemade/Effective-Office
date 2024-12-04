package band.effective.office.elevator.domain.validator

import band.effective.office.elevator.MainRes
import band.effective.office.elevator.domain.validator.ExtendedUserInfoValidator.Result.Invalid
import band.effective.office.elevator.domain.validator.ExtendedUserInfoValidator.Result.Valid

class ExtendedUserInfoValidatorImpl : ExtendedUserInfoValidator {

    override fun checkPhone(phoneNumber: String): ExtendedUserInfoValidator.Result {
        return when {
            phoneNumber.isEmpty() -> Invalid(MainRes.strings.error_empty_field)
            phoneNumber.length != PHONE_NUMBER_SIZE -> Invalid(MainRes.strings.error_phone_format)
            else -> Valid
        }
    }

    override fun checkName(name: String): ExtendedUserInfoValidator.Result {
        return when {
            name.isEmpty() ->
                Invalid(MainRes.strings.error_empty_field)
            name.any { it !in 'A'..'Z' && it !in 'a'..'z' && it != ' ' } ->
                Invalid(MainRes.strings.error_name_must_consist_of_latin_letters)
            else ->
                Valid
        }
    }

    override fun checkPost(post: String): ExtendedUserInfoValidator.Result {
        return when {
            post.isEmpty() -> Invalid(MainRes.strings.error_empty_field)
            else -> Valid
        }
    }

    override fun checkTelegramNick(telegram: String): ExtendedUserInfoValidator.Result {
        return when {
            telegram.isEmpty() ->
                Invalid(MainRes.strings.error_empty_field)
            !telegram.startsWith(char = '@', ignoreCase = true) ->
                Invalid(MainRes.strings.error_telegram_must_contain_at_symbol)
            else ->
                Valid
        }
    }

    companion object {

        const val PHONE_NUMBER_SIZE = 12
    }
}
