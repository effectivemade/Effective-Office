//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.user](../index.md)/[ApiKeyVerifier](index.md)/[isCorrectToken](is-correct-token.md)

# isCorrectToken

[jvm]\
open override fun [isCorrectToken](is-correct-token.md)(tokenString: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Check api key from input line. String encrypting by SHA-256 and comparing with encrypted keys from database. If it cannot find one, throw InstanceNotFoundException

#### Return

random String

#### Author

Kiselev Danil

#### Parameters

jvm

| | |
|---|---|
| tokenString | [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) which contains token to verify |
