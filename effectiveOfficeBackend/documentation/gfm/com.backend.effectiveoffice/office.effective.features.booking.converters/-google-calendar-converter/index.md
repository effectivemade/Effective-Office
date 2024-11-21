//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.converters](../index.md)/[GoogleCalendarConverter](index.md)

# GoogleCalendarConverter

[jvm]\
class [GoogleCalendarConverter](index.md)(calendarIdsRepository: [CalendarIdsRepository](../../office.effective.features.calendar.repository/-calendar-ids-repository/index.md), userRepository: [UserRepository](../../office.effective.features.user.repository/-user-repository/index.md), workspaceConverter: [WorkspaceFacadeConverter](../../office.effective.features.workspace.converters/-workspace-facade-converter/index.md), userConverter: [UserDTOModelConverter](../../office.effective.features.user.converters/-user-d-t-o-model-converter/index.md), bookingConverter: [BookingFacadeConverter](../-booking-facade-converter/index.md), verifier: [UuidValidator](../../office.effective.common.utils/-uuid-validator/index.md), workspaceRepository: [WorkspaceRepository](../../office.effective.features.workspace.repository/-workspace-repository/index.md))

Converts between Google Calendar Event and [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md) objects.

## Constructors

| | |
|---|---|
| [GoogleCalendarConverter](-google-calendar-converter.md) | [jvm]<br>constructor(calendarIdsRepository: [CalendarIdsRepository](../../office.effective.features.calendar.repository/-calendar-ids-repository/index.md), userRepository: [UserRepository](../../office.effective.features.user.repository/-user-repository/index.md), workspaceConverter: [WorkspaceFacadeConverter](../../office.effective.features.workspace.converters/-workspace-facade-converter/index.md), userConverter: [UserDTOModelConverter](../../office.effective.features.user.converters/-user-d-t-o-model-converter/index.md), bookingConverter: [BookingFacadeConverter](../-booking-facade-converter/index.md), verifier: [UuidValidator](../../office.effective.common.utils/-uuid-validator/index.md), workspaceRepository: [WorkspaceRepository](../../office.effective.features.workspace.repository/-workspace-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [toBookingDTO](to-booking-d-t-o.md) | [jvm]<br>fun [toBookingDTO](to-booking-d-t-o.md)(event: Event): [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md)<br>Converts Event to [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md) |
| [toBookingModel](to-booking-model.md) | [jvm]<br>fun [toBookingModel](to-booking-model.md)(event: Event): [Booking](../../office.effective.model/-booking/index.md)<br>Converts Event to [Booking](../../office.effective.model/-booking/index.md) |
| [toGoogleEvent](to-google-event.md) | [jvm]<br>fun [toGoogleEvent](to-google-event.md)(dto: [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md)): Event<br>Converts [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md) to Event. Event.description is used to indicate the booking author, because Event.organizer is defaultAccount of application<br>[jvm]<br>fun [toGoogleEvent](to-google-event.md)(model: [Booking](../../office.effective.model/-booking/index.md)): Event<br>Converts meeting room [Booking](../../office.effective.model/-booking/index.md) to Event. Event.description is used to indicate the booking author, because Event.organizer is defaultAccount of application |
| [toGoogleWorkspaceEvent](to-google-workspace-event.md) | [jvm]<br>fun [toGoogleWorkspaceEvent](to-google-workspace-event.md)(dto: [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md)): Event<br>Converts workspace [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md) to Event. Event.description is used to indicate the booking author, because Event.organizer is defaultAccount of application. Event.summary is used to indicate the booking workspace.<br>[jvm]<br>fun [toGoogleWorkspaceEvent](to-google-workspace-event.md)(model: [Booking](../../office.effective.model/-booking/index.md)): Event<br>Converts workspace [Booking](../../office.effective.model/-booking/index.md) to Event. Event.description is used to indicate the booking author, because Event.organizer is defaultAccount of application. Event.summary is used to indicate the booking workspace. |
| [toWorkspaceBooking](to-workspace-booking.md) | [jvm]<br>fun [toWorkspaceBooking](to-workspace-booking.md)(event: Event): [Booking](../../office.effective.model/-booking/index.md)<br>Converts Event to [Booking](../../office.effective.model/-booking/index.md) |
