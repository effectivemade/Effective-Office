//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.workspace.repository](../index.md)/[WorkspaceZones](index.md)

# WorkspaceZones

[jvm]\
object [WorkspaceZones](index.md) : Table&lt;[WorkspaceZoneEntity](../-workspace-zone-entity/index.md)&gt;

## Properties

| Name | Summary |
|---|---|
| [alias](index.md#643768950%2FProperties%2F-1216412040) | [jvm]<br>val [alias](index.md#643768950%2FProperties%2F-1216412040): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [catalog](index.md#1462050445%2FProperties%2F-1216412040) | [jvm]<br>val [catalog](index.md#1462050445%2FProperties%2F-1216412040): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [columns](index.md#2120772425%2FProperties%2F-1216412040) | [jvm]<br>val [columns](index.md#2120772425%2FProperties%2F-1216412040): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Column&lt;*&gt;&gt; |
| [entityClass](index.md#-154271151%2FProperties%2F-1216412040) | [jvm]<br>val [entityClass](index.md#-154271151%2FProperties%2F-1216412040): [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)&lt;[WorkspaceZoneEntity](../-workspace-zone-entity/index.md)&gt;? |
| [id](id.md) | [jvm]<br>val [id](id.md): Column&lt;[UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)&gt; |
| [name](name.md) | [jvm]<br>val [name](name.md): Column&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [primaryKeys](index.md#641857968%2FProperties%2F-1216412040) | [jvm]<br>val [primaryKeys](index.md#641857968%2FProperties%2F-1216412040): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Column&lt;*&gt;&gt; |
| [referencedKotlinType](index.md#1580874516%2FProperties%2F-1216412040) | [jvm]<br>val [referencedKotlinType](index.md#1580874516%2FProperties%2F-1216412040): [KType](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-type/index.html) |
| [referencedType](index.md#-1165976043%2FProperties%2F-1216412040) | [jvm]<br>val [referencedType](index.md#-1165976043%2FProperties%2F-1216412040): [Type](https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Type.html) |
| [schema](index.md#-1779279021%2FProperties%2F-1216412040) | [jvm]<br>val [schema](index.md#-1779279021%2FProperties%2F-1216412040): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |
| [tableName](index.md#-1061132051%2FProperties%2F-1216412040) | [jvm]<br>val [tableName](index.md#-1061132051%2FProperties%2F-1216412040): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Functions

| Name | Summary |
|---|---|
| [aliased](index.md#1316499710%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [aliased](index.md#1316499710%2FFunctions%2F-1216412040)(alias: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Table&lt;[WorkspaceZoneEntity](../-workspace-zone-entity/index.md)&gt; |
| [asExpression](index.md#-1780546710%2FFunctions%2F-1216412040) | [jvm]<br>fun [asExpression](index.md#-1780546710%2FFunctions%2F-1216412040)(): TableExpression |
| [bindTo](index.md#1501370637%2FExtensions%2F-1216412040) | [jvm]<br>inline fun &lt;[C](index.md#1501370637%2FExtensions%2F-1216412040) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; Column&lt;[C](index.md#1501370637%2FExtensions%2F-1216412040)&gt;.[bindTo](index.md#1501370637%2FExtensions%2F-1216412040)(selector: ([WorkspaceZoneEntity](../-workspace-zone-entity/index.md)) -&gt; [C](index.md#1501370637%2FExtensions%2F-1216412040)?): Column&lt;[C](index.md#1501370637%2FExtensions%2F-1216412040)&gt; |
| [createEntity](index.md#-1519680417%2FFunctions%2F-1216412040) | [jvm]<br>fun [createEntity](index.md#-1519680417%2FFunctions%2F-1216412040)(row: QueryRowSet, withReferences: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): [WorkspaceZoneEntity](../-workspace-zone-entity/index.md) |
| [equals](index.md#49267181%2FFunctions%2F-1216412040) | [jvm]<br>operator override fun [equals](index.md#49267181%2FFunctions%2F-1216412040)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [get](index.md#-353756012%2FFunctions%2F-1216412040) | [jvm]<br>operator fun [get](index.md#-353756012%2FFunctions%2F-1216412040)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Column&lt;*&gt; |
| [hashCode](index.md#-331409319%2FFunctions%2F-1216412040) | [jvm]<br>override fun [hashCode](index.md#-331409319%2FFunctions%2F-1216412040)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [primaryKey](index.md#525735072%2FExtensions%2F-1216412040) | [jvm]<br>fun &lt;[C](index.md#525735072%2FExtensions%2F-1216412040) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; Column&lt;[C](index.md#525735072%2FExtensions%2F-1216412040)&gt;.[primaryKey](index.md#525735072%2FExtensions%2F-1216412040)(): Column&lt;[C](index.md#525735072%2FExtensions%2F-1216412040)&gt; |
| [references](index.md#1287562215%2FExtensions%2F-1216412040) | [jvm]<br>inline fun &lt;[C](index.md#1287562215%2FExtensions%2F-1216412040) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), [R](index.md#1287562215%2FExtensions%2F-1216412040) : Entity&lt;[R](index.md#1287562215%2FExtensions%2F-1216412040)&gt;&gt; Column&lt;[C](index.md#1287562215%2FExtensions%2F-1216412040)&gt;.[references](index.md#1287562215%2FExtensions%2F-1216412040)(referenceTable: Table&lt;[R](index.md#1287562215%2FExtensions%2F-1216412040)&gt;, selector: ([WorkspaceZoneEntity](../-workspace-zone-entity/index.md)) -&gt; [R](index.md#1287562215%2FExtensions%2F-1216412040)?): Column&lt;[C](index.md#1287562215%2FExtensions%2F-1216412040)&gt; |
| [registerColumn](index.md#-1907218187%2FFunctions%2F-1216412040) | [jvm]<br>fun &lt;[C](index.md#-1907218187%2FFunctions%2F-1216412040) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [registerColumn](index.md#-1907218187%2FFunctions%2F-1216412040)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), sqlType: SqlType&lt;[C](index.md#-1907218187%2FFunctions%2F-1216412040)&gt;): Column&lt;[C](index.md#-1907218187%2FFunctions%2F-1216412040)&gt; |
| [toString](index.md#-509575384%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [toString](index.md#-509575384%2FFunctions%2F-1216412040)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [transform](index.md#675323752%2FExtensions%2F-1216412040) | [jvm]<br>fun &lt;[C](index.md#675323752%2FExtensions%2F-1216412040) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), [R](index.md#675323752%2FExtensions%2F-1216412040) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; Column&lt;[C](index.md#675323752%2FExtensions%2F-1216412040)&gt;.[transform](index.md#675323752%2FExtensions%2F-1216412040)(fromUnderlyingValue: ([C](index.md#675323752%2FExtensions%2F-1216412040)) -&gt; [R](index.md#675323752%2FExtensions%2F-1216412040), toUnderlyingValue: ([R](index.md#675323752%2FExtensions%2F-1216412040)) -&gt; [C](index.md#675323752%2FExtensions%2F-1216412040)): Column&lt;[R](index.md#675323752%2FExtensions%2F-1216412040)&gt; |
