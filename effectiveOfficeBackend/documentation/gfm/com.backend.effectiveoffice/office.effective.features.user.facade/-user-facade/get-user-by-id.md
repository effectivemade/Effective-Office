//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.user.facade](../index.md)/[UserFacade](index.md)/[getUserById](get-user-by-id.md)

# getUserById

[jvm]\
fun [getUserById](get-user-by-id.md)(userIdStr: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [UserDTO](../../office.effective.dto/-user-d-t-o/index.md)

Return user by id, or returns null, if user not found

#### Return

[UserDTO](../../office.effective.dto/-user-d-t-o/index.md)

#### Author

Kiselev Danil

#### Parameters

jvm

| | |
|---|---|
| userIdStr | string value of user id |

#### Throws

| | |
|---|---|
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | (UserModel::class, &quot;User with id ${userIdStr} not found&quot;, null) |
