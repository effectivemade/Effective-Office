package band.effective.office.elevator.ui.models.validator

interface ValidatorMethods {
    fun checkPhone(phoneNumber : String) : Boolean
    fun checkName(name : String) : Boolean
    fun checkPost(post : String) : Boolean
    fun checkTelegramNick(telegramNick : String) : Boolean
}