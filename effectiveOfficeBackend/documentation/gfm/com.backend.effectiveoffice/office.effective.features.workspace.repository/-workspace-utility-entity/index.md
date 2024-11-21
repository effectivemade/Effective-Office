//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.workspace.repository](../index.md)/[WorkspaceUtilityEntity](index.md)

# WorkspaceUtilityEntity

[jvm]\
interface [WorkspaceUtilityEntity](index.md) : Entity&lt;[WorkspaceUtilityEntity](index.md)&gt;

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) : Entity.Factory&lt;[WorkspaceUtilityEntity](index.md)&gt; |

## Properties

| Name | Summary |
|---|---|
| [count](count.md) | [jvm]<br>abstract var [count](count.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [entityClass](../-workspace-zone-entity/index.md#-361794977%2FProperties%2F-1216412040) | [jvm]<br>abstract val [entityClass](../-workspace-zone-entity/index.md#-361794977%2FProperties%2F-1216412040): [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)&lt;[WorkspaceUtilityEntity](index.md)&gt; |
| [properties](../-workspace-zone-entity/index.md#-795754829%2FProperties%2F-1216412040) | [jvm]<br>abstract val [properties](../-workspace-zone-entity/index.md#-795754829%2FProperties%2F-1216412040): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?&gt; |
| [utility](utility.md) | [jvm]<br>abstract var [utility](utility.md): [UtilityEntity](../-utility-entity/index.md) |
| [workspace](workspace.md) | [jvm]<br>abstract var [workspace](workspace.md): [WorkspaceEntity](../-workspace-entity/index.md) |

## Functions

| Name | Summary |
|---|---|
| [copy](../-workspace-zone-entity/index.md#-1367681679%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [copy](../-workspace-zone-entity/index.md#-1367681679%2FFunctions%2F-1216412040)(): [WorkspaceUtilityEntity](index.md) |
| [delete](../-workspace-zone-entity/index.md#1585744315%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [delete](../-workspace-zone-entity/index.md#1585744315%2FFunctions%2F-1216412040)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [discardChanges](../-workspace-zone-entity/index.md#-2020748447%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [discardChanges](../-workspace-zone-entity/index.md#-2020748447%2FFunctions%2F-1216412040)() |
| [equals](../-workspace-zone-entity/index.md#-1739296901%2FFunctions%2F-1216412040) | [jvm]<br>abstract operator override fun [equals](../-workspace-zone-entity/index.md#-1739296901%2FFunctions%2F-1216412040)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [flushChanges](../-workspace-zone-entity/index.md#-1059296249%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [flushChanges](../-workspace-zone-entity/index.md#-1059296249%2FFunctions%2F-1216412040)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [get](../-workspace-zone-entity/index.md#1251116358%2FFunctions%2F-1216412040) | [jvm]<br>abstract operator fun [get](../-workspace-zone-entity/index.md#1251116358%2FFunctions%2F-1216412040)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? |
| [hashCode](../-workspace-zone-entity/index.md#-265530613%2FFunctions%2F-1216412040) | [jvm]<br>abstract override fun [hashCode](../-workspace-zone-entity/index.md#-265530613%2FFunctions%2F-1216412040)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [set](../-workspace-zone-entity/index.md#267402869%2FFunctions%2F-1216412040) | [jvm]<br>abstract operator fun [set](../-workspace-zone-entity/index.md#267402869%2FFunctions%2F-1216412040)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), value: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?) |
| [toString](../-workspace-zone-entity/index.md#-443696678%2FFunctions%2F-1216412040) | [jvm]<br>abstract override fun [toString](../-workspace-zone-entity/index.md#-443696678%2FFunctions%2F-1216412040)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
