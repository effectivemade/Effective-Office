//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.user.converters](../index.md)/[UserModelEntityConverter](index.md)/[entityToModel](entity-to-model.md)

# entityToModel

[jvm]\
fun [entityToModel](entity-to-model.md)(userEntity: [UserEntity](../../office.effective.features.user.repository/-user-entity/index.md), integrations: [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[IntegrationModel](../../office.effective.model/-integration-model/index.md)&gt;?): [UserModel](../../office.effective.model/-user-model/index.md)

Converts [UserModel](../../office.effective.model/-user-model/index.md) to [UserEntity](../../office.effective.features.user.repository/-user-entity/index.md)

#### Return

converted [UserModel](../../office.effective.model/-user-model/index.md)

#### Author

Danil Kiselev, Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| userEntity | [UserEntity](../../office.effective.features.user.repository/-user-entity/index.md) to be converted |
| integrations | [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)<[IntegrationModel](../../office.effective.model/-integration-model/index.md)>? to add into user model |
