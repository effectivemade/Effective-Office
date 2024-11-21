package band.effective.office.elevator.ui.models.validator

open class UserInfoValidator : ValidatorMethods {

    override fun checkPhone(phoneNumber: String): Boolean = phoneNumber.length == phoneNumberSize

    override fun checkName(name: String): Boolean = name.isNotEmpty()

    override fun checkPost(post: String): Boolean = post.isNotEmpty()

    override fun checkTelegramNick(telegramNick: String): Boolean =
        telegramNick.isNotEmpty() && !telegramNick.contains(char = '@', ignoreCase = true)

    companion object {
       const val phoneNumberSize = 10
    }
}