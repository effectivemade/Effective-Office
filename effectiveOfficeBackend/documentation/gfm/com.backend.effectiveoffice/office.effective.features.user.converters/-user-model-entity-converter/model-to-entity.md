//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.user.converters](../index.md)/[UserModelEntityConverter](index.md)/[modelToEntity](model-to-entity.md)

# modelToEntity

[jvm]\
fun [modelToEntity](model-to-entity.md)(userModel: [UserModel](../../office.effective.model/-user-model/index.md)): [UserEntity](../../office.effective.features.user.repository/-user-entity/index.md)

Converts [UserModel](../../office.effective.model/-user-model/index.md) to [UserEntity](../../office.effective.features.user.repository/-user-entity/index.md)

#### Return

converted [UserEntity](../../office.effective.features.user.repository/-user-entity/index.md)

#### Author

Danil Kiselev, Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| userModel | [UserModel](../../office.effective.model/-user-model/index.md) to be converted |

#### Throws

| | |
|---|---|
| [MissingIdException](../../office.effective.common.exception/-missing-id-exception/index.md) | if the user id is null |
