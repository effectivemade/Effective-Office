//[com.backend.effectiveoffice](../../../index.md)/[office.effective.serviceapi](../index.md)/[IUserService](index.md)

# IUserService

interface [IUserService](index.md)

Interface that provides methods to manipulate [UserModel](../../office.effective.model/-user-model/index.md) objects

#### Inheritors

| |
|---|
| [UserService](../../office.effective.features.user.service/-user-service/index.md) |

## Functions

| Name | Summary |
|---|---|
| [getAll](get-all.md) | [jvm]<br>abstract fun [getAll](get-all.md)(): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[UserModel](../../office.effective.model/-user-model/index.md)&gt;<br>Return all users in database |
| [getUserByEmail](get-user-by-email.md) | [jvm]<br>abstract fun [getUserByEmail](get-user-by-email.md)(emailStr: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [UserModel](../../office.effective.model/-user-model/index.md)?<br>Return user with specified email |
| [getUserById](get-user-by-id.md) | [jvm]<br>abstract fun [getUserById](get-user-by-id.md)(userIdStr: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [UserModel](../../office.effective.model/-user-model/index.md)?<br>Return user by id, or returns null, if user not found |
| [getUsersByTag](get-users-by-tag.md) | [jvm]<br>abstract fun [getUsersByTag](get-users-by-tag.md)(tagStr: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[UserModel](../../office.effective.model/-user-model/index.md)&gt;<br>Return all users with given tag |
| [updateUser](update-user.md) | [jvm]<br>abstract fun [updateUser](update-user.md)(user: [UserModel](../../office.effective.model/-user-model/index.md)): [UserModel](../../office.effective.model/-user-model/index.md)<br>Change existed user in database |
