//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.auth.service](../index.md)/[TokenAuthorizer](index.md)

# TokenAuthorizer

[jvm]\
class [TokenAuthorizer](index.md)(extractor: [TokenExtractor](../-token-extractor/index.md) = TokenExtractor()) : [Authorizer](../-authorizer/index.md)

Implementation of [Authorizer](../-authorizer/index.md). Checks GoogleIdTokens

## Constructors

| | |
|---|---|
| [TokenAuthorizer](-token-authorizer.md) | [jvm]<br>constructor(extractor: [TokenExtractor](../-token-extractor/index.md) = TokenExtractor()) |

## Properties

| Name | Summary |
|---|---|
| [logger](logger.md) | [jvm]<br>val [logger](logger.md): Logger |

## Functions

| Name | Summary |
|---|---|
| [authorize](authorize.md) | [jvm]<br>open suspend override fun [authorize](authorize.md)(call: ApplicationCall): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Check Google ID Token using google library |
