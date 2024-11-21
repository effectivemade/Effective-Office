//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.repository](../index.md)/[BookingParticipantEntity](index.md)

# BookingParticipantEntity

[jvm]\
interface [BookingParticipantEntity](index.md) : Entity&lt;[BookingParticipantEntity](index.md)&gt;

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) : Entity.Factory&lt;[BookingParticipantEntity](index.md)&gt; |

## Properties

| Name | Summary |
|---|---|
| [booking](booking.md) | [jvm]<br>abstract var [booking](booking.md): [WorkspaceBookingEntity](../-workspace-booking-entity/index.md) |
| [entityClass](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md#-361794977%2FProperties%2F-1216412040) | [jvm]<br>abstract val [entityClass](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md#-361794977%2FProperties%2F-1216412040): [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)&lt;[BookingParticipantEntity](index.md)&gt; |
| [properties](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md#-795754829%2FProperties%2F-1216412040) | [jvm]<br>abstract val [properties](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md#-795754829%2FProperties%2F-1216412040): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?&gt; |
| [user](user.md) | [jvm]<br>abstract var [user](user.md): [UserEntity](../../office.effective.features.user.repository/-user-entity/index.md) |

## Functions

| Name | Summary |
|---|---|
| [copy](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md#-1367681679%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [copy](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md#-1367681679%2FFunctions%2F-1216412040)(): [BookingParticipantEntity](index.md) |
| [delete](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md#1585744315%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [delete](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md#1585744315%2FFunctions%2F-1216412040)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [discardChanges](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md#-2020748447%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [discardChanges](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md#-2020748447%2FFunctions%2F-1216412040)() |
| [equals](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md#-1739296901%2FFunctions%2F-1216412040) | [jvm]<br>abstract operator override fun [equals](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md#-1739296901%2FFunctions%2F-1216412040)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [flushChanges](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md#-1059296249%2FFunctions%2F-1216412040) | [jvm]<br>abstract fun [flushChanges](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md#-1059296249%2FFunctions%2F-1216412040)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [get](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md#1251116358%2FFunctions%2F-1216412040) | [jvm]<br>abstract operator fun [get](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md#1251116358%2FFunctions%2F-1216412040)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)? |
| [hashCode](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md#-265530613%2FFunctions%2F-1216412040) | [jvm]<br>abstract override fun [hashCode](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md#-265530613%2FFunctions%2F-1216412040)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [set](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md#267402869%2FFunctions%2F-1216412040) | [jvm]<br>abstract operator fun [set](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md#267402869%2FFunctions%2F-1216412040)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), value: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?) |
| [toString](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md#-443696678%2FFunctions%2F-1216412040) | [jvm]<br>abstract override fun [toString](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md#-443696678%2FFunctions%2F-1216412040)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
