//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.converters](../index.md)/[GoogleCalendarConverter](index.md)

# GoogleCalendarConverter

[jvm]\
class [GoogleCalendarConverter](index.md)(calendarIdsRepository: [CalendarIdsRepository](../../office.effective.features.calendar.repository/-calendar-ids-repository/index.md), userRepository: [UserRepository](../../office.effective.features.user.repository/-user-repository/index.md), verifier: [UuidValidator](../../office.effective.common.utils/-uuid-validator/index.md), workspaceRepository: [WorkspaceRepository](../../office.effective.features.workspace.repository/-workspace-repository/index.md))

Converts between Google Calendar Event and [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md) objects.

## Constructors

| | |
|---|---|
| [GoogleCalendarConverter](-google-calendar-converter.md) | [jvm]<br>constructor(calendarIdsRepository: [CalendarIdsRepository](../../office.effective.features.calendar.repository/-calendar-ids-repository/index.md), userRepository: [UserRepository](../../office.effective.features.user.repository/-user-repository/index.md), verifier: [UuidValidator](../../office.effective.common.utils/-uuid-validator/index.md), workspaceRepository: [WorkspaceRepository](../../office.effective.features.workspace.repository/-workspace-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [toGoogleWorkspaceMeetingEvent](to-google-workspace-meeting-event.md) | [jvm]<br>fun [toGoogleWorkspaceMeetingEvent](to-google-workspace-meeting-event.md)(model: [Booking](../../office.effective.model/-booking/index.md)): Event<br>Converts meeting workspace [Booking](../../office.effective.model/-booking/index.md) to Event. Event.description is used to indicate the booking author, because Event.organizer is defaultAccount of application. Event.summary is used to indicate the booking workspace. |
| [toGoogleWorkspaceRegularEvent](to-google-workspace-regular-event.md) | [jvm]<br>fun [toGoogleWorkspaceRegularEvent](to-google-workspace-regular-event.md)(model: [Booking](../../office.effective.model/-booking/index.md)): Event<br>Converts regular workspace [Booking](../../office.effective.model/-booking/index.md) to Event. Event.description is used to indicate the booking author, because Event.organizer is defaultAccount of application. Event.summary is used to indicate the booking workspace. |
| [toMeetingWorkspaceBooking](to-meeting-workspace-booking.md) | [jvm]<br>fun [toMeetingWorkspaceBooking](to-meeting-workspace-booking.md)(event: Event, owner: [UserModel](../../office.effective.model/-user-model/index.md)? = null, workspace: [Workspace](../../office.effective.model/-workspace/index.md)? = null, participants: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UserModel](../../office.effective.model/-user-model/index.md)&gt;? = null): [Booking](../../office.effective.model/-booking/index.md)<br>Converts meeting Event to [Booking](../../office.effective.model/-booking/index.md) |
| [toRegularWorkspaceBooking](to-regular-workspace-booking.md) | [jvm]<br>fun [toRegularWorkspaceBooking](to-regular-workspace-booking.md)(event: Event, owner: [UserModel](../../office.effective.model/-user-model/index.md)? = null, workspace: [Workspace](../../office.effective.model/-workspace/index.md)? = null, participants: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UserModel](../../office.effective.model/-user-model/index.md)&gt;? = null): [Booking](../../office.effective.model/-booking/index.md)<br>Converts regular Event to [Booking](../../office.effective.model/-booking/index.md) |
