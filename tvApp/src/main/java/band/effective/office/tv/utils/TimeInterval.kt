package band.effective.office.tv.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar

fun toEndEventTimeInterval(event: GregorianCalendar, eventName: String): String {
    val now = GregorianCalendar()
    val differenceInMinuts: Long = (event.timeInMillis - now.timeInMillis) / 60000
    val differenceInHours = differenceInMinuts / 60
    val differenceInDays = differenceInHours / 24
    val difference = if (differenceInDays > 0) {
        "$differenceInDays ${
            getCorrectDeclension(
                differenceInDays.toInt(), "день", "дня", "дней"
            )
        }"
    } else if (differenceInHours > 0) {
        "$differenceInHours ${
            getCorrectDeclension(
                differenceInHours.toInt(), "час", "часа", "часов"
            )
        }"
    } else "$differenceInMinuts ${
        getCorrectDeclension(
            differenceInMinuts.toInt(), "минута", "минуты", "минут"
        )
    }"
    return "$eventName закончится через $difference"
}

fun startEndTimeInterval(start: GregorianCalendar, finish: GregorianCalendar): String {
    val dateDateFormat = SimpleDateFormat("dd MMMM")
    val timeDateFormat = SimpleDateFormat("HH:mm")
    return if (start.get(Calendar.DAY_OF_MONTH) == finish.get(Calendar.DAY_OF_MONTH)) "${
        dateDateFormat.format(
            start.time
        )
    }, ${timeDateFormat.format(start.time)} - ${
        timeDateFormat.format(
            finish.time
        )
    }"
    else "${dateDateFormat.format(start.time)}, ${timeDateFormat.format(start.time)} - ${
        dateDateFormat.format(
            finish.time
        )
    }, ${timeDateFormat.format(finish.time)}"
}