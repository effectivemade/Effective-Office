//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.repository](../index.md)/[IBookingRepository](index.md)/[findById](find-by-id.md)

# findById

[jvm]\
abstract fun [findById](find-by-id.md)(bookingId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Booking](../../office.effective.model/-booking/index.md)?

Retrieves a booking model by its id. Retrieved booking contains user and workspace models without integrations and utilities

#### Return

[Booking](../../office.effective.model/-booking/index.md) with the given [bookingId](find-by-id.md) or null if booking with the given id doesn't exist

#### Author

Daniil Zavyalov, Danil Kiselev

#### Parameters

jvm

| | |
|---|---|
| bookingId | booking id |
