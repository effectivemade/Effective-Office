//[com.backend.effectiveoffice](../../../index.md)/[office.effective.dto](../index.md)/[UserDTO](index.md)

# UserDTO

[jvm]\
@Serializable

data class [UserDTO](index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val fullName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val active: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), val role: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val avatarUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val integrations: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[IntegrationDTO](../-integration-d-t-o/index.md)&gt;?, val email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val tag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

## Constructors

| | |
|---|---|
| [UserDTO](-user-d-t-o.md) | [jvm]<br>constructor(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), fullName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), active: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), role: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), avatarUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), integrations: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[IntegrationDTO](../-integration-d-t-o/index.md)&gt;?, email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), tag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [active](active.md) | [jvm]<br>val [active](active.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [avatarUrl](avatar-url.md) | [jvm]<br>val [avatarUrl](avatar-url.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [email](email.md) | [jvm]<br>val [email](email.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [fullName](full-name.md) | [jvm]<br>val [fullName](full-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [id](id.md) | [jvm]<br>val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [integrations](integrations.md) | [jvm]<br>val [integrations](integrations.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[IntegrationDTO](../-integration-d-t-o/index.md)&gt;? |
| [role](role.md) | [jvm]<br>val [role](role.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [tag](tag.md) | [jvm]<br>val [tag](tag.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
