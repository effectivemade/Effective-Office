//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.user.converters](../index.md)/[UserDTOModelConverter](index.md)/[dTOToModel](d-t-o-to-model.md)

# dTOToModel

[jvm]\
fun [dTOToModel](d-t-o-to-model.md)(userDTO: [UserDTO](../../office.effective.dto/-user-d-t-o/index.md)): [UserModel](../../office.effective.model/-user-model/index.md)

Converts [UserDTO](../../office.effective.dto/-user-d-t-o/index.md) to [UserModel](../../office.effective.model/-user-model/index.md). Search user tags in [UserRepository](../../office.effective.features.user.repository/-user-repository/index.md) by id. Takes user's integrations from DTO.

#### Return

converted [UserModel](../../office.effective.model/-user-model/index.md)

#### Author

Danil Kiselev, Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| userDTO | [UserDTO](../../office.effective.dto/-user-d-t-o/index.md) to be converted |
