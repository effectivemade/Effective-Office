//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.auth.service](../index.md)/[ApiKeyAuthorizer](index.md)/[authorize](authorize.md)

# authorize

[jvm]\
open suspend override fun [authorize](authorize.md)(call: ApplicationCall): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Check api key from input line. String encrypting by SHA-256 and comparing with encrypted keys from database. If it cannot find one, throw [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md)

#### Return

random String

#### Author

Kiselev Danil

#### Parameters

jvm

| | |
|---|---|
| call | ApplicationCall which contains token to verify |
