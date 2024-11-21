//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.auth.repository](../index.md)/[AuthRepository](index.md)/[findApiKey](find-api-key.md)

# findApiKey

[jvm]\
fun [findApiKey](find-api-key.md)(keyEncrypted: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

#### Return

encrypted api key [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

#### Author

Kiselev Danil

#### Parameters

jvm

| | |
|---|---|
| keyEncrypted | encrypted input [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

#### Throws

| | |
|---|---|
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | (ApiKeyEntity::class, &quot;No such key found&quot;) |
