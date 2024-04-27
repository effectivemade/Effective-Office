//[com.backend.effectiveoffice](../../index.md)/[office.effective.features.auth.service](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [ApiKeyAuthorizer](-api-key-authorizer/index.md) | [jvm]<br>class [ApiKeyAuthorizer](-api-key-authorizer/index.md)(extractor: [TokenExtractor](-token-extractor/index.md) = TokenExtractor()) : [Authorizer](-authorizer/index.md)<br>[Authorizer](-authorizer/index.md) implementation. Check api keys |
| [AuthorizationPipeline](-authorization-pipeline/index.md) | [jvm]<br>class [AuthorizationPipeline](-authorization-pipeline/index.md) : [Authorizer](-authorizer/index.md)<br>Encapsulate pipeline of authorizers |
| [Authorizer](-authorizer/index.md) | [jvm]<br>interface [Authorizer](-authorizer/index.md)<br>Interface for authorization chain of responsibility element |
| [PermitAuthorizer](-permit-authorizer/index.md) | [jvm]<br>class [PermitAuthorizer](-permit-authorizer/index.md)(permittedPaths: [Iterable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-iterable/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) : [Authorizer](-authorizer/index.md)<br>Authorizer to permit routes. Permit all child routes, if parent permitted. Example: &quot;/workspaces&quot; permission allow access to &quot;/workspaces/zones&quot; routes. |
| [RequestAuthorizer](-request-authorizer/index.md) | [jvm]<br>class [RequestAuthorizer](-request-authorizer/index.md)(extractor: [TokenExtractor](-token-extractor/index.md) = TokenExtractor()) : [Authorizer](-authorizer/index.md)<br>[Authorizer](-authorizer/index.md) implementation. Calls oauth2.googleapis.com to verify token |
| [TokenAuthorizer](-token-authorizer/index.md) | [jvm]<br>class [TokenAuthorizer](-token-authorizer/index.md)(extractor: [TokenExtractor](-token-extractor/index.md) = TokenExtractor()) : [Authorizer](-authorizer/index.md)<br>Implementation of [Authorizer](-authorizer/index.md). Checks GoogleIdTokens |
| [TokenExtractor](-token-extractor/index.md) | [jvm]<br>class [TokenExtractor](-token-extractor/index.md) |
