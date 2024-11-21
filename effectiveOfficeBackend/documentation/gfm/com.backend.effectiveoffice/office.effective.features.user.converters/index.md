//[com.backend.effectiveoffice](../../index.md)/[office.effective.features.user.converters](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [IntegrationDTOModelConverter](-integration-d-t-o-model-converter/index.md) | [jvm]<br>class [IntegrationDTOModelConverter](-integration-d-t-o-model-converter/index.md)(uuidConverter: [UuidValidator](../office.effective.common.utils/-uuid-validator/index.md))<br>Converters between [IntegrationDTO](../office.effective.dto/-integration-d-t-o/index.md) and [IntegrationModel](../office.effective.model/-integration-model/index.md) |
| [IntegrationModelEntityConverter](-integration-model-entity-converter/index.md) | [jvm]<br>class [IntegrationModelEntityConverter](-integration-model-entity-converter/index.md)<br>Converters between [IntegrationModel](../office.effective.model/-integration-model/index.md) and [IntegrationEntity](../office.effective.features.user.repository/-integration-entity/index.md) |
| [UserDTOModelConverter](-user-d-t-o-model-converter/index.md) | [jvm]<br>class [UserDTOModelConverter](-user-d-t-o-model-converter/index.md)(repository: [UserRepository](../office.effective.features.user.repository/-user-repository/index.md), converter: [IntegrationDTOModelConverter](-integration-d-t-o-model-converter/index.md), uuidConverter: [UuidValidator](../office.effective.common.utils/-uuid-validator/index.md))<br>Converters between [UserDTO](../office.effective.dto/-user-d-t-o/index.md) and [UserModel](../office.effective.model/-user-model/index.md) |
| [UserModelEntityConverter](-user-model-entity-converter/index.md) | [jvm]<br>class [UserModelEntityConverter](-user-model-entity-converter/index.md)<br>Converters between [UserModel](../office.effective.model/-user-model/index.md) and [UserEntity](../office.effective.features.user.repository/-user-entity/index.md) |
