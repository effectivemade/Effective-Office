//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.auth.service](../index.md)/[Authorizer](index.md)

# Authorizer

interface [Authorizer](index.md)

Interface for authorization chain of responsibility element

#### Inheritors

| |
|---|
| [ApiKeyAuthorizer](../-api-key-authorizer/index.md) |
| [AuthorizationPipeline](../-authorization-pipeline/index.md) |
| [PermitAuthorizer](../-permit-authorizer/index.md) |
| [RequestAuthorizer](../-request-authorizer/index.md) |
| [TokenAuthorizer](../-token-authorizer/index.md) |

## Functions

| Name | Summary |
|---|---|
| [authorize](authorize.md) | [jvm]<br>abstract suspend fun [authorize](authorize.md)(call: ApplicationCall): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
