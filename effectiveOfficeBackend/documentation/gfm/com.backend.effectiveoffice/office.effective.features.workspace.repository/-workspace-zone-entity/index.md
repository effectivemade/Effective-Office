//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.workspace.repository](../index.md)/[WorkspaceZoneEntity](index.md)

# WorkspaceZoneEntity

[jvm]\
interface [WorkspaceZoneEntity](index.md) : Entity&lt;[WorkspaceZoneEntity](index.md)&gt;

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) : Entity.Factory&lt;[WorkspaceZoneEntity](index.md)&gt; |

## Properties

| Name | Summary |
|---|---|
| [entityClass](index.md#-361794977%2FProperties%2F-1216412040) | [jvm]<br>abstract val [entityClass](index.md#-361794977%2FProperties%2F-1216412040): [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)&lt;[WorkspaceZoneEntity](index.md)&gt; |
| [id](id.md) | [jvm]<br>abstract var [id](id.md): [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html) |
| [name](name.md) | [jvm]<br>abstract var [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [properties](index.md#-795754829%2FProperties%2F-1216412040) | [jvm]<br>abstract val [properties](index.md#-795754829%2FProperties%2F-1216412040): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?&gt; |

## Functions

| Name | Summary |
|---|---|
| [copy](index.md#-1367681679%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [copy](index.md#-1367681679%2FFunctions%2F-1216412040)(): [WorkspaceZoneEntity](index.md) |
| [delete](index.md#1585744315%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [delete](index.md#1585744315%2FFunctions%2F-1216412040)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [discardChanges](index.md#-2020748447%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [discardChanges](index.md#-2020748447%2FFunctions%2F-1216412040)() |
| [equals](index.md#-1739296901%2FFunctions%2F-1216412040) | [jvm]<br>abstract operator override fun [equals](index.md#-1739296901%2FFunctions%2F-1216412040)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [flushChanges](index.md#-1059296249%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [flushChanges](index.md#-1059296249%2FFunctions%2F-1216412040)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [get](index.md#1251116358%2FFunctions%2F-1216412040) | [jvm]<br>abstract operator fun [get](index.md#1251116358%2FFunctions%2F-1216412040)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? |
| [hashCode](index.md#-265530613%2FFunctions%2F-1216412040) | [jvm]<br>abstract override fun [hashCode](index.md#-265530613%2FFunctions%2F-1216412040)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [set](index.md#267402869%2FFunctions%2F-1216412040) | [jvm]<br>abstract operator fun [set](index.md#267402869%2FFunctions%2F-1216412040)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), value: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?) |
| [toString](index.md#-443696678%2FFunctions%2F-1216412040) | [jvm]<br>abstract override fun [toString](index.md#-443696678%2FFunctions%2F-1216412040)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
