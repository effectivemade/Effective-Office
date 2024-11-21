//[com.backend.effectiveoffice](../../../index.md)/[office.effective.common.exception](../index.md)/[InstanceNotFoundException](index.md)

# InstanceNotFoundException

[jvm]\
class [InstanceNotFoundException](index.md)(targetClass: [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)&lt;*&gt;, message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), id: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)? = null) : [RuntimeException](https://docs.oracle.com/javase/8/docs/api/java/lang/RuntimeException.html)

Exception indicating that the requested resource was not found in the database.

## Constructors

| | |
|---|---|
| [InstanceNotFoundException](-instance-not-found-exception.md) | [jvm]<br>constructor(targetClass: [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)&lt;*&gt;, message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), id: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)? = null) |

## Properties

| Name | Summary |
|---|---|
| [cause](../-workspace-unavailable-exception/index.md#-654012527%2FProperties%2F-1216412040) | [jvm]<br>open val [cause](../-workspace-unavailable-exception/index.md#-654012527%2FProperties%2F-1216412040): [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)? |
| [message](../-workspace-unavailable-exception/index.md#1824300659%2FProperties%2F-1216412040) | [jvm]<br>open val [message](../-workspace-unavailable-exception/index.md#1824300659%2FProperties%2F-1216412040): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? |

## Functions

| Name | Summary |
|---|---|
| [addSuppressed](../-workspace-unavailable-exception/index.md#282858770%2FFunctions%2F-1216412040) | [jvm]<br>fun [addSuppressed](../-workspace-unavailable-exception/index.md#282858770%2FFunctions%2F-1216412040)(p0: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)) |
| [fillInStackTrace](../-workspace-unavailable-exception/index.md#-1102069925%2FFunctions%2F-1216412040) | [jvm]<br>open fun [fillInStackTrace](../-workspace-unavailable-exception/index.md#-1102069925%2FFunctions%2F-1216412040)(): [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) |
| [getLocalizedMessage](../-workspace-unavailable-exception/index.md#1043865560%2FFunctions%2F-1216412040) | [jvm]<br>open fun [getLocalizedMessage](../-workspace-unavailable-exception/index.md#1043865560%2FFunctions%2F-1216412040)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getStackTrace](../-workspace-unavailable-exception/index.md#2050903719%2FFunctions%2F-1216412040) | [jvm]<br>open fun [getStackTrace](../-workspace-unavailable-exception/index.md#2050903719%2FFunctions%2F-1216412040)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[StackTraceElement](https://docs.oracle.com/javase/8/docs/api/java/lang/StackTraceElement.html)&gt; |
| [getSuppressed](../-workspace-unavailable-exception/index.md#672492560%2FFunctions%2F-1216412040) | [jvm]<br>fun [getSuppressed](../-workspace-unavailable-exception/index.md#672492560%2FFunctions%2F-1216412040)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)&gt; |
| [initCause](../-workspace-unavailable-exception/index.md#-418225042%2FFunctions%2F-1216412040) | [jvm]<br>open fun [initCause](../-workspace-unavailable-exception/index.md#-418225042%2FFunctions%2F-1216412040)(p0: [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)): [Throwable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html) |
| [printStackTrace](../-workspace-unavailable-exception/index.md#-1769529168%2FFunctions%2F-1216412040) | [jvm]<br>open fun [printStackTrace](../-workspace-unavailable-exception/index.md#-1769529168%2FFunctions%2F-1216412040)()<br>open fun [printStackTrace](../-workspace-unavailable-exception/index.md#1841853697%2FFunctions%2F-1216412040)(p0: [PrintStream](https://docs.oracle.com/javase/8/docs/api/java/io/PrintStream.html))<br>open fun [printStackTrace](../-workspace-unavailable-exception/index.md#1175535278%2FFunctions%2F-1216412040)(p0: [PrintWriter](https://docs.oracle.com/javase/8/docs/api/java/io/PrintWriter.html)) |
| [setStackTrace](../-workspace-unavailable-exception/index.md#2135801318%2FFunctions%2F-1216412040) | [jvm]<br>open fun [setStackTrace](../-workspace-unavailable-exception/index.md#2135801318%2FFunctions%2F-1216412040)(p0: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[StackTraceElement](https://docs.oracle.com/javase/8/docs/api/java/lang/StackTraceElement.html)&gt;) |
