//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.repository](../index.md)/[IBookingRepository](index.md)/[findAll](find-all.md)

# findAll

[jvm]\
abstract fun [findAll](find-all.md)(eventRangeFrom: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), eventRangeTo: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Booking](../../office.effective.model/-booking/index.md)&gt;

Retrieves all bookings

#### Return

All [Booking](../../office.effective.model/-booking/index.md)s

#### Author

Daniil Zavyalov, Danil Kiselev

#### Parameters

jvm

| | |
|---|---|
| eventRangeFrom | use to set an upper bound for filtering bookings by start time |
| eventRangeTo | lover bound for filtering bookings by start time |
