//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.repository](../index.md)/[IBookingRepository](index.md)/[findAllByOwnerId](find-all-by-owner-id.md)

# findAllByOwnerId

[jvm]\
abstract fun [findAllByOwnerId](find-all-by-owner-id.md)(ownerId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html), eventRangeFrom: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), eventRangeTo: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null, returnInstances: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Booking](../../office.effective.model/-booking/index.md)&gt;

Returns all bookings with the given owner id

#### Return

List of all user [Booking](../../office.effective.model/-booking/index.md)

#### Author

Daniil Zavyalov, Danil Kiselev

#### Parameters

jvm

| |
|---|
| ownerId |
| eventRangeTo | use to set an upper bound for filtering bookings by start time |
| eventRangeFrom | lover bound for filtering bookings by start time |
| returnInstances | return recurring bookings as non-recurrent instances |
