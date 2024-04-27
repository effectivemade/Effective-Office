//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.auth.service](../index.md)/[RequestAuthorizer](index.md)/[authorize](authorize.md)

# authorize

[jvm]\
open suspend override fun [authorize](authorize.md)(call: ApplicationCall): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Check Google ID Token. Calls oauth2.googleapis.com

#### Return

is token correct

#### Author

Kiselev Danil

#### Parameters

jvm

| | |
|---|---|
| call | ApplicationCall which contains token to verify |
