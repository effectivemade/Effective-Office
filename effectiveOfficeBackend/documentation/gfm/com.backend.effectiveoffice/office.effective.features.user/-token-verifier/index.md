//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.user](../index.md)/[TokenVerifier](index.md)

# TokenVerifier

[jvm]\
class [TokenVerifier](index.md) : [ITokenVerifier](../-i-token-verifier/index.md)

Implementation of [ITokenVerifier](../-i-token-verifier/index.md). Checks GoogleIdTokens

## Constructors

| | |
|---|---|
| [TokenVerifier](-token-verifier.md) | [jvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [isCorrectToken](is-correct-token.md) | [jvm]<br>open override fun [isCorrectToken](is-correct-token.md)(tokenString: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Check Google Id Token from input line. Returns email |
