//[com.backend.effectiveoffice](../../../index.md)/[office.effective.dto](../index.md)/[WorkspaceDTO](index.md)

# WorkspaceDTO

[jvm]\
@Serializable

data class [WorkspaceDTO](index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val utilities: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UtilityDTO](../-utility-d-t-o/index.md)&gt;, val zone: [WorkspaceZoneDTO](../-workspace-zone-d-t-o/index.md)? = null, val tag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

## Constructors

| | |
|---|---|
| [WorkspaceDTO](-workspace-d-t-o.md) | [jvm]<br>constructor(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), utilities: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UtilityDTO](../-utility-d-t-o/index.md)&gt;, zone: [WorkspaceZoneDTO](../-workspace-zone-d-t-o/index.md)? = null, tag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [id](id.md) | [jvm]<br>val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [name](name.md) | [jvm]<br>val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [tag](tag.md) | [jvm]<br>val [tag](tag.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [utilities](utilities.md) | [jvm]<br>val [utilities](utilities.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UtilityDTO](../-utility-d-t-o/index.md)&gt; |
| [zone](zone.md) | [jvm]<br>val [zone](zone.md): [WorkspaceZoneDTO](../-workspace-zone-d-t-o/index.md)? = null |
