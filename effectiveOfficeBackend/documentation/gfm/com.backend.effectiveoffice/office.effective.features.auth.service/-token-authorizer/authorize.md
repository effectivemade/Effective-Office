//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.auth.service](../index.md)/[TokenAuthorizer](index.md)/[authorize](authorize.md)

# authorize

[jvm]\
open suspend override fun [authorize](authorize.md)(call: ApplicationCall): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Check Google ID Token using google library

#### Author

Kiselev Danil

Kiselev Danil

#### Return

is token correct

#### Parameters

jvm

| | |
|---|---|
| call | [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) which contains token to verify |

#### Throws

| | |
|---|---|
| [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) | (&quot;Token wasn't verified by Google&quot;) if token does not contain payload |
