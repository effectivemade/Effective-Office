//[com.backend.effectiveoffice](../../index.md)/[office.effective.plugins](index.md)

# Package-level declarations

## Properties

| Name | Summary |
|---|---|
| [applicationHttpClient](application-http-client.md) | [jvm]<br>val [applicationHttpClient](application-http-client.md): HttpClient |
| [CustomAuthorizationPlugin](-custom-authorization-plugin.md) | [jvm]<br>val [CustomAuthorizationPlugin](-custom-authorization-plugin.md): ApplicationPlugin&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;<br>Allows to check Authentication plugins automatically. Run every time when receiving input call. Checks Authentication (bearer) header containment |

## Functions

| Name | Summary |
|---|---|
| [configureAuthentication](configure-authentication.md) | [jvm]<br>fun Application.[configureAuthentication](configure-authentication.md)() |
| [configureDI](configure-d-i.md) | [jvm]<br>fun Application.[configureDI](configure-d-i.md)()<br>Koin dependency injection modules |
| [configureExceptionHandling](configure-exception-handling.md) | [jvm]<br>fun Application.[configureExceptionHandling](configure-exception-handling.md)() |
| [configureMigration](configure-migration.md) | [jvm]<br>fun Application.[configureMigration](configure-migration.md)()<br>Run database migrations at application startup if MIGRATIONS_ENABLE is true |
| [configureRouting](configure-routing.md) | [jvm]<br>fun Application.[configureRouting](configure-routing.md)() |
| [configureSerialization](configure-serialization.md) | [jvm]<br>fun Application.[configureSerialization](configure-serialization.md)() |
| [configureSwagger](configure-swagger.md) | [jvm]<br>fun Application.[configureSwagger](configure-swagger.md)() |
| [configureValidation](configure-validation.md) | [jvm]<br>fun Application.[configureValidation](configure-validation.md)() |
