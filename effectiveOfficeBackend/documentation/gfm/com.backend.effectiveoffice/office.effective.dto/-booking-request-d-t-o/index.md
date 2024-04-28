//[com.backend.effectiveoffice](../../../index.md)/[office.effective.dto](../index.md)/[BookingRequestDTO](index.md)

# BookingRequestDTO

[jvm]\
@Serializable

data class [BookingRequestDTO](index.md)(val ownerEmail: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val participantEmails: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, val workspaceId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val beginBooking: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), val endBooking: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), val recurrence: [RecurrenceDTO](../../model/-recurrence-d-t-o/index.md)? = null)

## Constructors

| | |
|---|---|
| [BookingRequestDTO](-booking-request-d-t-o.md) | [jvm]<br>constructor(ownerEmail: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, participantEmails: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, workspaceId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), beginBooking: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), endBooking: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), recurrence: [RecurrenceDTO](../../model/-recurrence-d-t-o/index.md)? = null) |

## Properties

| Name | Summary |
|---|---|
| [beginBooking](begin-booking.md) | [jvm]<br>val [beginBooking](begin-booking.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [endBooking](end-booking.md) | [jvm]<br>val [endBooking](end-booking.md): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [ownerEmail](owner-email.md) | [jvm]<br>val [ownerEmail](owner-email.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [participantEmails](participant-emails.md) | [jvm]<br>val [participantEmails](participant-emails.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [recurrence](recurrence.md) | [jvm]<br>val [recurrence](recurrence.md): [RecurrenceDTO](../../model/-recurrence-d-t-o/index.md)? = null |
| [workspaceId](workspace-id.md) | [jvm]<br>val [workspaceId](workspace-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
