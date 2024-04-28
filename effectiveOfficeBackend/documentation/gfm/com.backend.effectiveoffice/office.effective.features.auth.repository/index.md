//[com.backend.effectiveoffice](../../index.md)/[office.effective.features.auth.repository](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [ApiKeyEntity](-api-key-entity/index.md) | [jvm]<br>interface [ApiKeyEntity](-api-key-entity/index.md) : Entity&lt;[ApiKeyEntity](-api-key-entity/index.md)&gt; |
| [ApiKeys](-api-keys/index.md) | [jvm]<br>object [ApiKeys](-api-keys/index.md) : Table&lt;[ApiKeyEntity](-api-key-entity/index.md)&gt; |
| [AuthRepository](-auth-repository/index.md) | [jvm]<br>class [AuthRepository](-auth-repository/index.md)(db: Database)<br>Provides methods to access to api_keys table in database |

## Properties

| Name | Summary |
|---|---|
| [apikeys](apikeys.md) | [jvm]<br>val Database.[apikeys](apikeys.md): EntitySequence&lt;[ApiKeyEntity](-api-key-entity/index.md), [ApiKeys](-api-keys/index.md)&gt; |
