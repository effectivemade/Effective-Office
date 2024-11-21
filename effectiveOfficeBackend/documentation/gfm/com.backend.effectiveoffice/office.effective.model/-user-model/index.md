//[com.backend.effectiveoffice](../../../index.md)/[office.effective.model](../index.md)/[UserModel](index.md)

# UserModel

[jvm]\
data class [UserModel](index.md)(var fullName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), var id: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)?, var tag: [UsersTagEntity](../../office.effective.features.user.repository/-users-tag-entity/index.md)?, var active: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), var role: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, var avatarURL: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, var integrations: [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[IntegrationModel](../-integration-model/index.md)&gt;?, var email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

## Constructors

| | |
|---|---|
| [UserModel](-user-model.md) | [jvm]<br>constructor(fullName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), id: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)?, tag: [UsersTagEntity](../../office.effective.features.user.repository/-users-tag-entity/index.md)?, active: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), role: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, avatarURL: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, integrations: [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[IntegrationModel](../-integration-model/index.md)&gt;?, email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [active](active.md) | [jvm]<br>var [active](active.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [avatarURL](avatar-u-r-l.md) | [jvm]<br>var [avatarURL](avatar-u-r-l.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [email](email.md) | [jvm]<br>var [email](email.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [fullName](full-name.md) | [jvm]<br>var [fullName](full-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [id](id.md) | [jvm]<br>var [id](id.md): [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)? |
| [integrations](integrations.md) | [jvm]<br>var [integrations](integrations.md): [Set](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-set/index.html)&lt;[IntegrationModel](../-integration-model/index.md)&gt;? |
| [role](role.md) | [jvm]<br>var [role](role.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [tag](tag.md) | [jvm]<br>var [tag](tag.md): [UsersTagEntity](../../office.effective.features.user.repository/-users-tag-entity/index.md)? |
