//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.user.repository](../index.md)/[UserRepository](index.md)/[findSetOfIntegrationsByUser](find-set-of-integrations-by-user.md)

# findSetOfIntegrationsByUser

[jvm]\
fun [findSetOfIntegrationsByUser](find-set-of-integrations-by-user.md)(userId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): [MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)&lt;[IntegrationModel](../../office.effective.model/-integration-model/index.md)&gt;

Returns all integrations of user (by user id)

#### Return

[MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)<[IntegrationModel](../../office.effective.model/-integration-model/index.md)>

#### Author

Danil Kiselev, Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| userId | user id in [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html) format |

#### Throws

| | |
|---|---|
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | ([UserEntity](../-user-entity/index.md)::class, &quot;User $userId&quot;) |
