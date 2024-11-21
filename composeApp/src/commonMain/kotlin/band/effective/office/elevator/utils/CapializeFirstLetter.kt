package band.effective.office.elevator.utils

fun capitalizeFirstLetter(str: String)  =
    str[0].uppercase() + str.slice(IntRange(start = 1, endInclusive = str.length - 1))