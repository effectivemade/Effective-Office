//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.converters](../index.md)/[BookingRepositoryConverter](index.md)

# BookingRepositoryConverter

[jvm]\
class [BookingRepositoryConverter](index.md)(database: Database, workspaceConverter: [WorkspaceRepositoryConverter](../../office.effective.features.workspace.converters/-workspace-repository-converter/index.md), userConverter: [UserModelEntityConverter](../../office.effective.features.user.converters/-user-model-entity-converter/index.md), uuidValidator: [UuidValidator](../../office.effective.common.utils/-uuid-validator/index.md))

Converts between [Booking](../../office.effective.model/-booking/index.md) and [WorkspaceBookingEntity](../../office.effective.features.booking.repository/-workspace-booking-entity/index.md) objects. Uses [UserModelEntityConverter](../../office.effective.features.user.converters/-user-model-entity-converter/index.md) and [WorkspaceRepositoryConverter](../../office.effective.features.workspace.converters/-workspace-repository-converter/index.md) to convert contained users and workspaces

Performs own database queries to user and workspace entities

## Constructors

| | |
|---|---|
| [BookingRepositoryConverter](-booking-repository-converter.md) | [jvm]<br>constructor(database: Database, workspaceConverter: [WorkspaceRepositoryConverter](../../office.effective.features.workspace.converters/-workspace-repository-converter/index.md), userConverter: [UserModelEntityConverter](../../office.effective.features.user.converters/-user-model-entity-converter/index.md), uuidValidator: [UuidValidator](../../office.effective.common.utils/-uuid-validator/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [entityToModel](entity-to-model.md) | [jvm]<br>fun [entityToModel](entity-to-model.md)(bookingEntity: [WorkspaceBookingEntity](../../office.effective.features.booking.repository/-workspace-booking-entity/index.md), participants: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UserEntity](../../office.effective.features.user.repository/-user-entity/index.md)&gt;): [Booking](../../office.effective.model/-booking/index.md)<br>Converts booking entity to model which contains user and workspace models. |
| [modelToEntity](model-to-entity.md) | [jvm]<br>fun [modelToEntity](model-to-entity.md)(bookingModel: [Booking](../../office.effective.model/-booking/index.md)): [WorkspaceBookingEntity](../../office.effective.features.booking.repository/-workspace-booking-entity/index.md)<br>Converts booking model to entity. Uses ids from contained [UserModel](../../office.effective.model/-user-model/index.md) and [Workspace](../../office.effective.model/-workspace/index.md) to find actual entities in database |
