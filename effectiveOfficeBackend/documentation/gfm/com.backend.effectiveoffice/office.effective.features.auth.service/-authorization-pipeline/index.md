//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.auth.service](../index.md)/[AuthorizationPipeline](index.md)

# AuthorizationPipeline

[jvm]\
class [AuthorizationPipeline](index.md) : [Authorizer](../-authorizer/index.md)

Encapsulate pipeline of authorizers

## Constructors

| | |
|---|---|
| [AuthorizationPipeline](-authorization-pipeline.md) | [jvm]<br>constructor() |

## Properties

| Name | Summary |
|---|---|
| [logger](logger.md) | [jvm]<br>val [logger](logger.md): Logger |

## Functions

| Name | Summary |
|---|---|
| [addAuthorizer](add-authorizer.md) | [jvm]<br>fun [addAuthorizer](add-authorizer.md)(authorizer: [Authorizer](../-authorizer/index.md)): [AuthorizationPipeline](index.md)<br>Allow you to add authorizer to pipeline |
| [authorize](authorize.md) | [jvm]<br>open suspend override fun [authorize](authorize.md)(call: ApplicationCall): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
