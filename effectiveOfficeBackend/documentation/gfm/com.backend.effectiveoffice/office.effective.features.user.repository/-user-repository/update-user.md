//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.user.repository](../index.md)/[UserRepository](index.md)/[updateUser](update-user.md)

# updateUser

[jvm]\
fun [updateUser](update-user.md)(model: [UserModel](../../office.effective.model/-user-model/index.md)): [UserModel](../../office.effective.model/-user-model/index.md)

Updates a given user. User searched in db by id. Use the returned model for further operations.

#### Return

[UserModel](../../office.effective.model/-user-model/index.md) updated user model

#### Author

Danil Kiselev, Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| model | [UserModel](../../office.effective.model/-user-model/index.md) with updated information |

#### Throws

| | |
|---|---|
| [MissingIdException](../../office.effective.common.exception/-missing-id-exception/index.md) | if the user or integration id is null |
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | if the given user or integration don't exist |
