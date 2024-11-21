//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.facade](../index.md)/[BookingFacade](index.md)/[findAll](find-all.md)

# findAll

[jvm]\
fun [findAll](find-all.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, workspaceId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, bookingRangeTo: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?, bookingRangeFrom: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) = BookingConstants.MIN_SEARCH_START_TIME): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md)&gt;

Returns all bookings. Bookings can be filtered by owner and workspace id

#### Return

[BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md) list

#### Author

Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| userId | use to filter by booking owner id. Should be valid UUID |
| workspaceId | use to filter by booking workspace id. Should be valid UUID |
| bookingRangeTo | upper bound (exclusive) for a beginBooking to filter by. Optional. Should be greater than range_from. |
| bookingRangeFrom | lower bound (exclusive) for a endBooking to filter by. Should be lover than [bookingRangeFrom](find-all.md). Default value: [BookingConstants.MIN_SEARCH_START_TIME](../../office.effective.common.constants/-booking-constants/-m-i-n_-s-e-a-r-c-h_-s-t-a-r-t_-t-i-m-e.md) |
