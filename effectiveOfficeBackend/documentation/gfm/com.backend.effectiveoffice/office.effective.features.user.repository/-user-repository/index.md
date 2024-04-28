//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.user.repository](../index.md)/[UserRepository](index.md)

# UserRepository

[jvm]\
class [UserRepository](index.md)(db: Database, converter: [UserModelEntityConverter](../../office.effective.features.user.converters/-user-model-entity-converter/index.md), integrationConverter: [IntegrationModelEntityConverter](../../office.effective.features.user.converters/-integration-model-entity-converter/index.md))

Perform database queries with users

## Constructors

| | |
|---|---|
| [UserRepository](-user-repository.md) | [jvm]<br>constructor(db: Database, converter: [UserModelEntityConverter](../../office.effective.features.user.converters/-user-model-entity-converter/index.md), integrationConverter: [IntegrationModelEntityConverter](../../office.effective.features.user.converters/-integration-model-entity-converter/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [existsById](exists-by-id.md) | [jvm]<br>fun [existsById](exists-by-id.md)(userId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Checks existence of user by id, using count |
| [findAll](find-all.md) | [jvm]<br>fun [findAll](find-all.md)(): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[UserModel](../../office.effective.model/-user-model/index.md)&gt;<br>Retrieves all users |
| [findAllByEmails](find-all-by-emails.md) | [jvm]<br>fun [findAllByEmails](find-all-by-emails.md)(emails: [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UserModel](../../office.effective.model/-user-model/index.md)&gt;<br>Retrieves users models with integrations by emails. If a user with one of the specified emails does not exist, that email will be ignored. |
| [findAllIntegrationsByUserIds](find-all-integrations-by-user-ids.md) | [jvm]<br>fun [findAllIntegrationsByUserIds](find-all-integrations-by-user-ids.md)(ids: [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)&lt;[UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)&gt;): [HashMap](https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html)&lt;[UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html), [MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)&lt;[IntegrationModel](../../office.effective.model/-integration-model/index.md)&gt;&gt;<br>Returns a HashMap that maps user ids and their integrations |
| [findByEmail](find-by-email.md) | [jvm]<br>fun [findByEmail](find-by-email.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [UserModel](../../office.effective.model/-user-model/index.md)?<br>Retrieves a user model by email |
| [findById](find-by-id.md) | [jvm]<br>fun [findById](find-by-id.md)(userId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): [UserModel](../../office.effective.model/-user-model/index.md)?<br>Retrieves user model by id |
| [findByTag](find-by-tag.md) | [jvm]<br>fun [findByTag](find-by-tag.md)(tagId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[UserModel](../../office.effective.model/-user-model/index.md)&gt;<br>Used to find multiple users with one tag |
| [findSetOfIntegrationsByUser](find-set-of-integrations-by-user.md) | [jvm]<br>fun [findSetOfIntegrationsByUser](find-set-of-integrations-by-user.md)(userId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): [MutableSet](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-set/index.html)&lt;[IntegrationModel](../../office.effective.model/-integration-model/index.md)&gt;<br>Returns all integrations of user (by user id) |
| [findTagByName](find-tag-by-name.md) | [jvm]<br>fun [findTagByName](find-tag-by-name.md)(tagName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [UserTagModel](../../office.effective.model/-user-tag-model/index.md)<br>Returns [UserTagModel](../../office.effective.model/-user-tag-model/index.md) by tag name |
| [findTagByUserOrNull](find-tag-by-user-or-null.md) | [jvm]<br>fun [findTagByUserOrNull](find-tag-by-user-or-null.md)(userId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): [UsersTagEntity](../-users-tag-entity/index.md)?<br>Returns Tag entity or null, if user with such id doesn't exist |
| [updateUser](update-user.md) | [jvm]<br>fun [updateUser](update-user.md)(model: [UserModel](../../office.effective.model/-user-model/index.md)): [UserModel](../../office.effective.model/-user-model/index.md)<br>Updates a given user. User searched in db by id. Use the returned model for further operations. |
