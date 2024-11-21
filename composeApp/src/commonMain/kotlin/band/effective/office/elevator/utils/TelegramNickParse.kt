package band.effective.office.elevator.utils

fun telegramNickParse(telegramURL: String): String {
    val indexChar = telegramURL.indexOfLast { it == '/' }
    return if (indexChar != -1)
                telegramURL.substring(indexChar)
            else
                telegramURL.replace("@", "")
}