//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.converters](../index.md)/[BookingRepositoryConverter](index.md)/[modelToEntity](model-to-entity.md)

# modelToEntity

[jvm]\
fun [modelToEntity](model-to-entity.md)(bookingModel: [Booking](../../office.effective.model/-booking/index.md)): [WorkspaceBookingEntity](../../office.effective.features.booking.repository/-workspace-booking-entity/index.md)

Converts booking model to entity. Uses ids from contained [UserModel](../../office.effective.model/-user-model/index.md) and [Workspace](../../office.effective.model/-workspace/index.md) to find actual entities in database

#### Return

The resulting [WorkspaceBookingEntity](../../office.effective.features.booking.repository/-workspace-booking-entity/index.md) object

#### Author

Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| bookingModel | [Booking](../../office.effective.model/-booking/index.md) to be converted |

#### Throws

| | |
|---|---|
| [MissingIdException](../../office.effective.common.exception/-missing-id-exception/index.md) | if the booking, owner or workspace id is null |
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | if the given user or workspace don't exist |
