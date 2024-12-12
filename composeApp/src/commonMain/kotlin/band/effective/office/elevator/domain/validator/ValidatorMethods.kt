package band.effective.office.elevator.domain.validator

interface ValidatorMethods {
    fun checkPhone(phoneNumber : String) : Boolean
    fun checkName(name : String) : Boolean
    fun checkPost(post : String) : Boolean
    fun checkTelegramNick(telegramNick : String) : Boolean
}
