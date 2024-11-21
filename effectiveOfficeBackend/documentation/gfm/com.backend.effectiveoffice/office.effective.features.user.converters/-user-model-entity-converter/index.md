//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.user.converters](../index.md)/[UserModelEntityConverter](index.md)

# UserModelEntityConverter

[jvm]\
class [UserModelEntityConverter](index.md)

Converters between [UserModel](../../office.effective.model/-user-model/index.md) and [UserEntity](../../office.effective.features.user.repository/-user-entity/index.md)

## Constructors

| | |
|---|---|
| [UserModelEntityConverter](-user-model-entity-converter.md) | [jvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [entityToModel](entity-to-model.md) | [jvm]<br>fun [entityToModel](entity-to-model.md)(userEntity: [UserEntity](../../office.effective.features.user.repository/-user-entity/index.md), integrations: [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[IntegrationModel](../../office.effective.model/-integration-model/index.md)&gt;?): [UserModel](../../office.effective.model/-user-model/index.md)<br>Converts [UserModel](../../office.effective.model/-user-model/index.md) to [UserEntity](../../office.effective.features.user.repository/-user-entity/index.md) |
| [modelToEntity](model-to-entity.md) | [jvm]<br>fun [modelToEntity](model-to-entity.md)(userModel: [UserModel](../../office.effective.model/-user-model/index.md)): [UserEntity](../../office.effective.features.user.repository/-user-entity/index.md)<br>Converts [UserModel](../../office.effective.model/-user-model/index.md) to [UserEntity](../../office.effective.features.user.repository/-user-entity/index.md) |
