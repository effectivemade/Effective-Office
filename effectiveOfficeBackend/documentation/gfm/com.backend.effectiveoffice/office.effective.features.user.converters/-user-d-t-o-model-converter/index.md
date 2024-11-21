//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.user.converters](../index.md)/[UserDTOModelConverter](index.md)

# UserDTOModelConverter

[jvm]\
class [UserDTOModelConverter](index.md)(repository: [UserRepository](../../office.effective.features.user.repository/-user-repository/index.md), converter: [IntegrationDTOModelConverter](../-integration-d-t-o-model-converter/index.md), uuidConverter: [UuidValidator](../../office.effective.common.utils/-uuid-validator/index.md))

Converters between [UserDTO](../../office.effective.dto/-user-d-t-o/index.md) and [UserModel](../../office.effective.model/-user-model/index.md)

## Constructors

| | |
|---|---|
| [UserDTOModelConverter](-user-d-t-o-model-converter.md) | [jvm]<br>constructor(repository: [UserRepository](../../office.effective.features.user.repository/-user-repository/index.md), converter: [IntegrationDTOModelConverter](../-integration-d-t-o-model-converter/index.md), uuidConverter: [UuidValidator](../../office.effective.common.utils/-uuid-validator/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [dTOToModel](d-t-o-to-model.md) | [jvm]<br>fun [dTOToModel](d-t-o-to-model.md)(userDTO: [UserDTO](../../office.effective.dto/-user-d-t-o/index.md)): [UserModel](../../office.effective.model/-user-model/index.md)<br>Converts [UserDTO](../../office.effective.dto/-user-d-t-o/index.md) to [UserModel](../../office.effective.model/-user-model/index.md). Search user tags in [UserRepository](../../office.effective.features.user.repository/-user-repository/index.md) by id. Takes user's integrations from DTO. |
| [modelToDTO](model-to-d-t-o.md) | [jvm]<br>fun [modelToDTO](model-to-d-t-o.md)(userModel: [UserModel](../../office.effective.model/-user-model/index.md)): [UserDTO](../../office.effective.dto/-user-d-t-o/index.md)<br>Converts [UserModel](../../office.effective.model/-user-model/index.md) to [UserDTO](../../office.effective.dto/-user-d-t-o/index.md) |
