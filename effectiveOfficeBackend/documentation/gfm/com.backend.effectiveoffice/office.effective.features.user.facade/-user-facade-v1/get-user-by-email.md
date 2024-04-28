//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.user.facade](../index.md)/[UserFacadeV1](index.md)/[getUserByEmail](get-user-by-email.md)

# getUserByEmail

[jvm]\
fun [getUserByEmail](get-user-by-email.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [UserDTO](../../office.effective.dto/-user-d-t-o/index.md)

Return user by email

#### Return

[UserDTO](../../office.effective.dto/-user-d-t-o/index.md)

#### Author

Kiselev Danil, Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| email | user email |

#### Throws

| | |
|---|---|
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | (UserModel::class, &quot;User with email $email not found&quot;, null) |
