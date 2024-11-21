//[com.backend.effectiveoffice](../../../index.md)/[office.effective.common.notifications](../index.md)/[FcmNotificationSender](index.md)

# FcmNotificationSender

[jvm]\
class [FcmNotificationSender](index.md)(fcm: FirebaseMessaging) : [INotificationSender](../-i-notification-sender/index.md)

Class for sending Firebase cloud messages

## Constructors

| | |
|---|---|
| [FcmNotificationSender](-fcm-notification-sender.md) | [jvm]<br>constructor(fcm: FirebaseMessaging) |

## Functions

| Name | Summary |
|---|---|
| [sendContentMessage](send-content-message.md) | [jvm]<br>open override fun [sendContentMessage](send-content-message.md)(action: HttpMethod, modifiedBooking: [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md))<br>Sends an FCM message about booking modification. Uses &quot;booking&quot; topic<br>[jvm]<br>open override fun [sendContentMessage](send-content-message.md)(action: HttpMethod, modifiedUser: [UserDTO](../../office.effective.dto/-user-d-t-o/index.md))<br>Sends an FCM message about user modification. Uses &quot;user&quot; topic<br>[jvm]<br>open override fun [sendContentMessage](send-content-message.md)(action: HttpMethod, modifiedWorkspace: [WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md))<br>Sends an FCM message about workspace modification. Uses &quot;workspace&quot; topic |
| [sendEmptyMessage](send-empty-message.md) | [jvm]<br>open override fun [sendEmptyMessage](send-empty-message.md)(topic: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Sends empty FCM message on topic |
