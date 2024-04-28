//[com.backend.effectiveoffice](../../index.md)/[office.effective.features.booking.repository](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [BookingMeetingRepository](-booking-meeting-repository/index.md) | [jvm]<br>class [BookingMeetingRepository](-booking-meeting-repository/index.md)(calendarIdsRepository: [CalendarIdsRepository](../office.effective.features.calendar.repository/-calendar-ids-repository/index.md), userRepository: [UserRepository](../office.effective.features.user.repository/-user-repository/index.md), calendar: Calendar, googleCalendarConverter: [GoogleCalendarConverter](../office.effective.features.booking.converters/-google-calendar-converter/index.md)) : [IBookingRepository](-i-booking-repository/index.md)<br>Class that executes Google calendar queries for booking meeting rooms |
| [BookingParticipantEntity](-booking-participant-entity/index.md) | [jvm]<br>interface [BookingParticipantEntity](-booking-participant-entity/index.md) : Entity&lt;[BookingParticipantEntity](-booking-participant-entity/index.md)&gt; |
| [BookingParticipants](-booking-participants/index.md) | [jvm]<br>object [BookingParticipants](-booking-participants/index.md) : Table&lt;[BookingParticipantEntity](-booking-participant-entity/index.md)&gt; |
| [BookingRegularRepository](-booking-regular-repository/index.md) | [jvm]<br>class [BookingRegularRepository](-booking-regular-repository/index.md)(calendar: Calendar, googleCalendarConverter: [GoogleCalendarConverter](../office.effective.features.booking.converters/-google-calendar-converter/index.md)) : [IBookingRepository](-i-booking-repository/index.md)<br>Class that executes Google calendar queries for booking regular workspaces |
| [BookingRepository](-booking-repository/index.md) | [jvm]<br>class [~~BookingRepository~~](-booking-repository/index.md)(database: Database, converter: [BookingRepositoryConverter](../office.effective.features.booking.converters/-booking-repository-converter/index.md), uuidValidator: [UuidValidator](../office.effective.common.utils/-uuid-validator/index.md))<br>Class that executes database queries with bookings |
| [IBookingRepository](-i-booking-repository/index.md) | [jvm]<br>interface [IBookingRepository](-i-booking-repository/index.md)<br>Interface of repository to manipulate with workspace bookings |
| [WorkspaceBooking](-workspace-booking/index.md) | [jvm]<br>object [WorkspaceBooking](-workspace-booking/index.md) : Table&lt;[WorkspaceBookingEntity](-workspace-booking-entity/index.md)&gt; |
| [WorkspaceBookingEntity](-workspace-booking-entity/index.md) | [jvm]<br>interface [WorkspaceBookingEntity](-workspace-booking-entity/index.md) : Entity&lt;[WorkspaceBookingEntity](-workspace-booking-entity/index.md)&gt; |

## Properties

| Name | Summary |
|---|---|
| [bookingParticipants](booking-participants.md) | [jvm]<br>val Database.[bookingParticipants](booking-participants.md): EntitySequence&lt;[BookingParticipantEntity](-booking-participant-entity/index.md), [BookingParticipants](-booking-participants/index.md)&gt; |
| [workspaceBooking](workspace-booking.md) | [jvm]<br>val Database.[workspaceBooking](workspace-booking.md): EntitySequence&lt;[WorkspaceBookingEntity](-workspace-booking-entity/index.md), [WorkspaceBooking](-workspace-booking/index.md)&gt; |
