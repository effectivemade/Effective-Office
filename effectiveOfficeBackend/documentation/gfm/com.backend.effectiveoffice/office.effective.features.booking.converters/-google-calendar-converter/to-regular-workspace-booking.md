//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.converters](../index.md)/[GoogleCalendarConverter](index.md)/[toRegularWorkspaceBooking](to-regular-workspace-booking.md)

# toRegularWorkspaceBooking

[jvm]\
fun [toRegularWorkspaceBooking](to-regular-workspace-booking.md)(event: Event, owner: [UserModel](../../office.effective.model/-user-model/index.md)? = null, workspace: [Workspace](../../office.effective.model/-workspace/index.md)? = null, participants: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UserModel](../../office.effective.model/-user-model/index.md)&gt;? = null): [Booking](../../office.effective.model/-booking/index.md)

Converts regular Event to [Booking](../../office.effective.model/-booking/index.md)

Creates placeholders if workspace or owner doesn't exist in database

#### Return

The resulting [Booking](../../office.effective.model/-booking/index.md) object

#### Parameters

jvm

| | |
|---|---|
| event | Event to be converted |
| owner | specify this parameter to reduce the number of database queries if the owner has already been retrieved |
| participants | specify this parameter to reduce the number of database queries if participants have already been retrieved |
| workspace | specify this parameter to reduce the number of database queries if workspace have already been retrieved |
