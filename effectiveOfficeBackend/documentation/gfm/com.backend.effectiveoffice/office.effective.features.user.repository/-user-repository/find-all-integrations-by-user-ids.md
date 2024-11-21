//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.user.repository](../index.md)/[UserRepository](index.md)/[findAllIntegrationsByUserIds](find-all-integrations-by-user-ids.md)

# findAllIntegrationsByUserIds

[jvm]\
fun [findAllIntegrationsByUserIds](find-all-integrations-by-user-ids.md)(ids: [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)&lt;[UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)&gt;): [HashMap](https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html)&lt;[UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html), [MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)&lt;[IntegrationModel](../../office.effective.model/-integration-model/index.md)&gt;&gt;

Returns a HashMap that maps user ids and their integrations

#### Return

[HashMap](https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html)<[UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html), [MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)<[IntegrationModel](../../office.effective.model/-integration-model/index.md)>>

#### Author

Daniil Zavyalov, Danil Kiselev

#### Parameters

jvm

| | |
|---|---|
| ids | [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)<[UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)> of users id |

#### Throws

| | |
|---|---|
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | if user with the given id doesn't exist in the database |
