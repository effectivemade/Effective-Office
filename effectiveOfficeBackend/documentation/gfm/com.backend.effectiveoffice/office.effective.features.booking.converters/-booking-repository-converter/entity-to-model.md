//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.converters](../index.md)/[BookingRepositoryConverter](index.md)/[entityToModel](entity-to-model.md)

# entityToModel

[jvm]\
fun [entityToModel](entity-to-model.md)(bookingEntity: [WorkspaceBookingEntity](../../office.effective.features.booking.repository/-workspace-booking-entity/index.md), participants: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UserEntity](../../office.effective.features.user.repository/-user-entity/index.md)&gt;): [Booking](../../office.effective.model/-booking/index.md)

Converts booking entity to model which contains user and workspace models.

User/workspace models contain an empty list of integrations/utilities. Use WorkspaceRepository and UserRepository to find it

TODO: this method can create only Bookings without recurrence

#### Return

The resulting [Booking](../../office.effective.model/-booking/index.md) object

#### Author

Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| bookingEntity | [WorkspaceBookingEntity](../../office.effective.features.booking.repository/-workspace-booking-entity/index.md) to be converted |
| participants | users who participate in the booking |
