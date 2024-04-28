//[com.backend.effectiveoffice](../../../index.md)/[office.effective.model](../index.md)/[Booking](index.md)

# Booking

[jvm]\
data class [Booking](index.md)(var owner: [UserModel](../-user-model/index.md)?, var participants: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UserModel](../-user-model/index.md)&gt;, var workspace: [Workspace](../-workspace/index.md), var id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, var beginBooking: [Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html), var endBooking: [Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html), var recurrence: [RecurrenceModel](../-recurrence-model/index.md)?, val recurringBookingId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null)

## Constructors

| | |
|---|---|
| [Booking](-booking.md) | [jvm]<br>constructor(owner: [UserModel](../-user-model/index.md)?, participants: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UserModel](../-user-model/index.md)&gt;, workspace: [Workspace](../-workspace/index.md), id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, beginBooking: [Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html), endBooking: [Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html), recurrence: [RecurrenceModel](../-recurrence-model/index.md)?, recurringBookingId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null) |

## Properties

| Name | Summary |
|---|---|
| [beginBooking](begin-booking.md) | [jvm]<br>var [beginBooking](begin-booking.md): [Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html) |
| [endBooking](end-booking.md) | [jvm]<br>var [endBooking](end-booking.md): [Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html) |
| [id](id.md) | [jvm]<br>var [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [owner](owner.md) | [jvm]<br>var [owner](owner.md): [UserModel](../-user-model/index.md)? |
| [participants](participants.md) | [jvm]<br>var [participants](participants.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UserModel](../-user-model/index.md)&gt; |
| [recurrence](recurrence.md) | [jvm]<br>var [recurrence](recurrence.md): [RecurrenceModel](../-recurrence-model/index.md)? |
| [recurringBookingId](recurring-booking-id.md) | [jvm]<br>val [recurringBookingId](recurring-booking-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null |
| [workspace](workspace.md) | [jvm]<br>var [workspace](workspace.md): [Workspace](../-workspace/index.md) |
