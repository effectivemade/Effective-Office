//[com.backend.effectiveoffice](../../../index.md)/[office.effective.dto](../index.md)/[BookingResponseDTO](index.md)

# BookingResponseDTO

[jvm]\
@Serializable

data class [BookingResponseDTO](index.md)(val owner: [UserDTO](../-user-d-t-o/index.md)?, val participants: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UserDTO](../-user-d-t-o/index.md)&gt;, val workspace: [WorkspaceDTO](../-workspace-d-t-o/index.md), val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val beginBooking: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), val endBooking: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), val recurrence: [RecurrenceDTO](../../model/-recurrence-d-t-o/index.md)? = null, val recurringBookingId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null)

## Constructors

| | |
|---|---|
| [BookingResponseDTO](-booking-response-d-t-o.md) | [jvm]<br>constructor(owner: [UserDTO](../-user-d-t-o/index.md)?, participants: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UserDTO](../-user-d-t-o/index.md)&gt;, workspace: [WorkspaceDTO](../-workspace-d-t-o/index.md), id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), beginBooking: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), endBooking: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), recurrence: [RecurrenceDTO](../../model/-recurrence-d-t-o/index.md)? = null, recurringBookingId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) |

## Properties

| Name | Summary |
|---|---|
| [beginBooking](begin-booking.md) | [jvm]<br>val [beginBooking](begin-booking.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [endBooking](end-booking.md) | [jvm]<br>val [endBooking](end-booking.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [id](id.md) | [jvm]<br>val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [owner](owner.md) | [jvm]<br>val [owner](owner.md): [UserDTO](../-user-d-t-o/index.md)? |
| [participants](participants.md) | [jvm]<br>val [participants](participants.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UserDTO](../-user-d-t-o/index.md)&gt; |
| [recurrence](recurrence.md) | [jvm]<br>val [recurrence](recurrence.md): [RecurrenceDTO](../../model/-recurrence-d-t-o/index.md)? = null |
| [recurringBookingId](recurring-booking-id.md) | [jvm]<br>val [recurringBookingId](recurring-booking-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [workspace](workspace.md) | [jvm]<br>val [workspace](workspace.md): [WorkspaceDTO](../-workspace-d-t-o/index.md) |
