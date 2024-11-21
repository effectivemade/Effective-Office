//[com.backend.effectiveoffice](../../../index.md)/[office.effective.common.notifications](../index.md)/[INotificationSender](index.md)

# INotificationSender

interface [INotificationSender](index.md)

Interface for sending notifications on topics

#### Inheritors

| |
|---|
| [FcmNotificationSender](../-fcm-notification-sender/index.md) |

## Functions

| Name | Summary |
|---|---|
| [sendContentMessage](send-content-message.md) | [jvm]<br>abstract fun [sendContentMessage](send-content-message.md)(action: HttpMethod, modifiedBooking: [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md))<br>Sends message about booking modification<br>[jvm]<br>abstract fun [sendContentMessage](send-content-message.md)(action: HttpMethod, modifiedUser: [UserDTO](../../office.effective.dto/-user-d-t-o/index.md))<br>Sends message about user modification<br>[jvm]<br>abstract fun [sendContentMessage](send-content-message.md)(action: HttpMethod, modifiedWorkspace: [WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md))<br>Sends message about workspace modification |
| [sendEmptyMessage](send-empty-message.md) | [jvm]<br>abstract fun [sendEmptyMessage](send-empty-message.md)(topic: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Sends message about topic modification |
