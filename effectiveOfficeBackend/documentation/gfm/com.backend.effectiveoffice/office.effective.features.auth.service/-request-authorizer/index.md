//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.auth.service](../index.md)/[RequestAuthorizer](index.md)

# RequestAuthorizer

[jvm]\
class [RequestAuthorizer](index.md)(extractor: [TokenExtractor](../-token-extractor/index.md) = TokenExtractor()) : [Authorizer](../-authorizer/index.md)

[Authorizer](../-authorizer/index.md) implementation. Calls oauth2.googleapis.com to verify token

## Constructors

| | |
|---|---|
| [RequestAuthorizer](-request-authorizer.md) | [jvm]<br>constructor(extractor: [TokenExtractor](../-token-extractor/index.md) = TokenExtractor()) |

## Properties

| Name | Summary |
|---|---|
| [logger](logger.md) | [jvm]<br>val [logger](logger.md): Logger |

## Functions

| Name | Summary |
|---|---|
| [authorize](authorize.md) | [jvm]<br>open suspend override fun [authorize](authorize.md)(call: ApplicationCall): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Check Google ID Token. Calls oauth2.googleapis.com |
