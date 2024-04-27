//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.auth.service](../index.md)/[PermitAuthorizer](index.md)

# PermitAuthorizer

[jvm]\
class [PermitAuthorizer](index.md)(permittedPaths: [Iterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterable/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) : [Authorizer](../-authorizer/index.md)

Authorizer to permit routes. Permit all child routes, if parent permitted. Example: &quot;/workspaces&quot; permission allow access to &quot;/workspaces/zones&quot; routes.

#### Author

Danil Kiselev

## Constructors

| | |
|---|---|
| [PermitAuthorizer](-permit-authorizer.md) | [jvm]<br>constructor(permittedPaths: [Iterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterable/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) |

## Properties

| Name | Summary |
|---|---|
| [logger](logger.md) | [jvm]<br>val [logger](logger.md): Logger |

## Functions

| Name | Summary |
|---|---|
| [authorize](authorize.md) | [jvm]<br>open suspend override fun [authorize](authorize.md)(call: ApplicationCall): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
