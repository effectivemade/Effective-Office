//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.auth.service](../index.md)/[ApiKeyAuthorizer](index.md)

# ApiKeyAuthorizer

[jvm]\
class [ApiKeyAuthorizer](index.md)(extractor: [TokenExtractor](../-token-extractor/index.md) = TokenExtractor()) : [Authorizer](../-authorizer/index.md)

[Authorizer](../-authorizer/index.md) implementation. Check api keys

## Constructors

| | |
|---|---|
| [ApiKeyAuthorizer](-api-key-authorizer.md) | [jvm]<br>constructor(extractor: [TokenExtractor](../-token-extractor/index.md) = TokenExtractor()) |

## Properties

| Name | Summary |
|---|---|
| [logger](logger.md) | [jvm]<br>val [logger](logger.md): Logger |

## Functions

| Name | Summary |
|---|---|
| [authorize](authorize.md) | [jvm]<br>open suspend override fun [authorize](authorize.md)(call: ApplicationCall): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Check api key from input line. String encrypting by SHA-256 and comparing with encrypted keys from database. If it cannot find one, throw [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) |
