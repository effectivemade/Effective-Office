//[com.backend.effectiveoffice](../../../index.md)/[office.effective.common.notifications](../index.md)/[INotificationSender](index.md)/[sendContentMessage](send-content-message.md)

# sendContentMessage

[jvm]\
abstract fun [sendContentMessage](send-content-message.md)(action: HttpMethod, modifiedWorkspace: [WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md))

Sends message about workspace modification

#### Author

Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| action | will be put as &quot;action&quot; in message data |
| modifiedWorkspace | will be put as &quot;object&quot; in message data |

[jvm]\
abstract fun [sendContentMessage](send-content-message.md)(action: HttpMethod, modifiedUser: [UserDTO](../../office.effective.dto/-user-d-t-o/index.md))

Sends message about user modification

#### Author

Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| action | will be put as &quot;action&quot; in message data |
| modifiedUser | will be put as &quot;object&quot; in message data |

[jvm]\
abstract fun [sendContentMessage](send-content-message.md)(action: HttpMethod, modifiedBooking: [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md))

Sends message about booking modification

#### Author

Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| action | will be put as &quot;action&quot; in message data |
| modifiedBooking | will be put as &quot;object&quot; in message data |
