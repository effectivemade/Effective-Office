//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.user.facade](../index.md)/[UserFacadeV1](index.md)

# UserFacadeV1

[jvm]\
class [UserFacadeV1](index.md)(service: [IUserService](../../office.effective.serviceapi/-i-user-service/index.md), converterDTO: [UserDTOModelConverter](../../office.effective.features.user.converters/-user-d-t-o-model-converter/index.md), transactionManager: [DatabaseTransactionManager](../../office.effective.common.utils/-database-transaction-manager/index.md))

Class used in routes to handle user-related requests. Provides business transaction, data conversion and validation.

## Constructors

| | |
|---|---|
| [UserFacadeV1](-user-facade-v1.md) | [jvm]<br>constructor(service: [IUserService](../../office.effective.serviceapi/-i-user-service/index.md), converterDTO: [UserDTOModelConverter](../../office.effective.features.user.converters/-user-d-t-o-model-converter/index.md), transactionManager: [DatabaseTransactionManager](../../office.effective.common.utils/-database-transaction-manager/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [getUserByEmail](get-user-by-email.md) | [jvm]<br>fun [getUserByEmail](get-user-by-email.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [UserDTO](../../office.effective.dto/-user-d-t-o/index.md)<br>Return user by email |
| [getUserById](get-user-by-id.md) | [jvm]<br>fun [getUserById](get-user-by-id.md)(userIdStr: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [UserDTO](../../office.effective.dto/-user-d-t-o/index.md)<br>Return user by id, or returns null, if user not found |
| [getUsers](get-users.md) | [jvm]<br>fun [getUsers](get-users.md)(): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[UserDTO](../../office.effective.dto/-user-d-t-o/index.md)&gt;<br>Retrieves all users |
| [getUsersByTag](get-users-by-tag.md) | [jvm]<br>fun [getUsersByTag](get-users-by-tag.md)(tagStr: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[UserDTO](../../office.effective.dto/-user-d-t-o/index.md)&gt;<br>Return all users with given tag |
| [updateUser](update-user.md) | [jvm]<br>fun [updateUser](update-user.md)(user: [UserDTO](../../office.effective.dto/-user-d-t-o/index.md)): [UserDTO](../../office.effective.dto/-user-d-t-o/index.md)<br>Updates a given user. Use the returned model for further operations |
