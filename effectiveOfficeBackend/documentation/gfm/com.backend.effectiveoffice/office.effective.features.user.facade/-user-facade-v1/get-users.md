//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.user.facade](../index.md)/[UserFacadeV1](index.md)/[getUsers](get-users.md)

# getUsers

[jvm]\
fun [getUsers](get-users.md)(): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[UserDTO](../../office.effective.dto/-user-d-t-o/index.md)&gt;

Retrieves all users

#### Return

[Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)<[UserDTO](../../office.effective.dto/-user-d-t-o/index.md)>

#### Author

Daniil Zavyalov

#### Throws

| | |
|---|---|
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | (UserModel::class, &quot;User with id ${userIdStr} not found&quot;, null) |
