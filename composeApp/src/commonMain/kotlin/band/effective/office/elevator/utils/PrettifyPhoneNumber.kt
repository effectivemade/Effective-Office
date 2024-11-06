package band.effective.office.elevator.utils

private const val FORMAT = "+# ### ### ##-##"

internal fun prettifyPhoneNumber(rawPhoneNumber: String): String? {
    var formatIndex = 0
    var inputIndex = 0

    val stringBuilder = StringBuilder()
    while (formatIndex < FORMAT.length && inputIndex < rawPhoneNumber.length) {
        val currentFormatChar = FORMAT[formatIndex]
        val currentInputChar = rawPhoneNumber[inputIndex]

        if (currentFormatChar == '#') {
            if (currentInputChar !in '0'..'9') {
                return null
            }
            stringBuilder.append(currentInputChar)
            inputIndex++
        } else {
            if (currentFormatChar == currentInputChar) {
                inputIndex++
            }
            stringBuilder.append(currentFormatChar)
        }
        formatIndex++
    }

    if (inputIndex != rawPhoneNumber.length || formatIndex != FORMAT.length) {
        return null
    }

    return stringBuilder.toString()
}
