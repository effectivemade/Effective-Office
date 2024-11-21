//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.user](../index.md)/[ApiKeyVerifier](index.md)

# ApiKeyVerifier

[jvm]\
class [ApiKeyVerifier](index.md) : [ITokenVerifier](../-i-token-verifier/index.md)

[ITokenVerifier](../-i-token-verifier/index.md) implementation. Needs to check api keys

## Constructors

| | |
|---|---|
| [ApiKeyVerifier](-api-key-verifier.md) | [jvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [isCorrectToken](is-correct-token.md) | [jvm]<br>open override fun [isCorrectToken](is-correct-token.md)(tokenString: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Check api key from input line. String encrypting by SHA-256 and comparing with encrypted keys from database. If it cannot find one, throw InstanceNotFoundException |
