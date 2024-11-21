package band.effective.office.elevator.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


// returns the date at the current time
fun getCurrentDate(timeZone: TimeZone = TimeZone.currentSystemDefault()) =
    Clock.System.now().toLocalDateTime(timeZone).date