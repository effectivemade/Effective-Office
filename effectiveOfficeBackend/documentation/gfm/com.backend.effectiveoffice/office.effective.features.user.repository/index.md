//[com.backend.effectiveoffice](../../index.md)/[office.effective.features.user.repository](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [IntegrationEntity](-integration-entity/index.md) | [jvm]<br>interface [IntegrationEntity](-integration-entity/index.md) : Entity&lt;[IntegrationEntity](-integration-entity/index.md)&gt; |
| [Integrations](-integrations/index.md) | [jvm]<br>object [Integrations](-integrations/index.md) : Table&lt;[IntegrationEntity](-integration-entity/index.md)&gt; |
| [UserEntity](-user-entity/index.md) | [jvm]<br>interface [UserEntity](-user-entity/index.md) : Entity&lt;[UserEntity](-user-entity/index.md)&gt; |
| [UserIntegrationEntity](-user-integration-entity/index.md) | [jvm]<br>interface [UserIntegrationEntity](-user-integration-entity/index.md) : Entity&lt;[UserIntegrationEntity](-user-integration-entity/index.md)&gt; |
| [UserRepository](-user-repository/index.md) | [jvm]<br>class [UserRepository](-user-repository/index.md)(db: Database, converter: [UserModelEntityConverter](../office.effective.features.user.converters/-user-model-entity-converter/index.md), integrationConverter: [IntegrationModelEntityConverter](../office.effective.features.user.converters/-integration-model-entity-converter/index.md))<br>Perform database queries with users |
| [Users](-users/index.md) | [jvm]<br>object [Users](-users/index.md) : Table&lt;[UserEntity](-user-entity/index.md)&gt; |
| [UsersIntegrations](-users-integrations/index.md) | [jvm]<br>object [UsersIntegrations](-users-integrations/index.md) : Table&lt;[UserIntegrationEntity](-user-integration-entity/index.md)&gt; |
| [UsersTagEntity](-users-tag-entity/index.md) | [jvm]<br>interface [UsersTagEntity](-users-tag-entity/index.md) : Entity&lt;[UsersTagEntity](-users-tag-entity/index.md)&gt; |
| [UsersTags](-users-tags/index.md) | [jvm]<br>object [UsersTags](-users-tags/index.md) : Table&lt;[UsersTagEntity](-users-tag-entity/index.md)&gt; |

## Properties

| Name | Summary |
|---|---|
| [integrations](integrations.md) | [jvm]<br>val Database.[integrations](integrations.md): EntitySequence&lt;[IntegrationEntity](-integration-entity/index.md), [Integrations](-integrations/index.md)&gt; |
| [users](users.md) | [jvm]<br>val Database.[users](users.md): EntitySequence&lt;[UserEntity](-user-entity/index.md), [Users](-users/index.md)&gt; |
| [users_tags](users_tags.md) | [jvm]<br>val Database.[users_tags](users_tags.md): EntitySequence&lt;[UsersTagEntity](-users-tag-entity/index.md), [UsersTags](-users-tags/index.md)&gt; |
| [usersinegrations](usersinegrations.md) | [jvm]<br>val Database.[usersinegrations](usersinegrations.md): EntitySequence&lt;[UserIntegrationEntity](-user-integration-entity/index.md), [UsersIntegrations](-users-integrations/index.md)&gt; |
