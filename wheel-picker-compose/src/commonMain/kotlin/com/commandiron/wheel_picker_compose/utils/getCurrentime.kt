package com.commandiron.wheel_picker_compose.utils

import com.commandiron.wheel_picker_compose.core.SnappedDateTime
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun getCurrentTime(timeZone: TimeZone = TimeZone.currentSystemDefault()) =
    Clock.System.now().toLocalDateTime(timeZone).time

val LocalTime.Companion.Max
    get() = LocalTime(
        hour = 23,
        minute = 59,
        second = 59,
        nanosecond = 999999999
    )

val LocalTime.Companion.Min
    get() = LocalTime(
        hour = 0,
        minute = 0,
        second = 0,
        nanosecond = 0
    )

fun LocalTime.Companion.of(
    hour: Int,
    minute: Int
) = LocalTime(hour = hour, minute = minute, second = 0, nanosecond = 0)

fun LocalTime.truncatedToMinute() = LocalTime.of(
    hour = hour,
    minute = minute
)

fun LocalTime.withHour(hour: Int) =
    LocalTime(hour = hour, minute = minute, second = second, nanosecond = nanosecond)

fun LocalTime.withMinute(minute: Int) =
    LocalTime(hour = hour, minute = minute, second = second, nanosecond = nanosecond)

fun LocalTime.isBefore(other: LocalTime) = this < other

fun LocalTime.isAfter(other: LocalTime) = this > other