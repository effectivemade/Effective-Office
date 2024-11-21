//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.user.service](../index.md)/[UserService](index.md)

# UserService

[jvm]\
class [UserService](index.md)(repository: [UserRepository](../../office.effective.features.user.repository/-user-repository/index.md)) : [IUserService](../../office.effective.serviceapi/-i-user-service/index.md)

Class that provides methods to manipulate [UserModel](../../office.effective.model/-user-model/index.md) objects

## Constructors

| | |
|---|---|
| [UserService](-user-service.md) | [jvm]<br>constructor(repository: [UserRepository](../../office.effective.features.user.repository/-user-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [getAll](get-all.md) | [jvm]<br>open override fun [getAll](get-all.md)(): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[UserModel](../../office.effective.model/-user-model/index.md)&gt;<br>Retrieves all users |
| [getUserByEmail](get-user-by-email.md) | [jvm]<br>open override fun [getUserByEmail](get-user-by-email.md)(emailStr: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [UserModel](../../office.effective.model/-user-model/index.md)?<br>Return user with specified email |
| [getUserById](get-user-by-id.md) | [jvm]<br>open override fun [getUserById](get-user-by-id.md)(userIdStr: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [UserModel](../../office.effective.model/-user-model/index.md)?<br>Return user by id, or returns null, if user not found |
| [getUsersByTag](get-users-by-tag.md) | [jvm]<br>open override fun [getUsersByTag](get-users-by-tag.md)(tagStr: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[UserModel](../../office.effective.model/-user-model/index.md)&gt;<br>Return all users with given tag |
| [updateUser](update-user.md) | [jvm]<br>open override fun [updateUser](update-user.md)(user: [UserModel](../../office.effective.model/-user-model/index.md)): [UserModel](../../office.effective.model/-user-model/index.md)<br>Updates a given user. Use the returned model for further operations |
