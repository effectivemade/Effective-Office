//[com.backend.effectiveoffice](../../index.md)/[office.effective.common.exception](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [InstanceNotFoundException](-instance-not-found-exception/index.md) | [jvm]<br>class [InstanceNotFoundException](-instance-not-found-exception/index.md)(targetClass: [KClass](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html)&lt;*&gt;, message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), id: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)? = null) : [RuntimeException](https://docs.oracle.com/javase/8/docs/api/java/lang/RuntimeException.html)<br>Exception indicating that the requested resource was not found in the database. |
| [MissingIdException](-missing-id-exception/index.md) | [jvm]<br>class [MissingIdException](-missing-id-exception/index.md)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [RuntimeException](https://docs.oracle.com/javase/8/docs/api/java/lang/RuntimeException.html)<br>Exception indicating that object id is null, although not null value expected |
| [ValidationException](-validation-exception/index.md) | [jvm]<br>class [ValidationException](-validation-exception/index.md)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [RuntimeException](https://docs.oracle.com/javase/8/docs/api/java/lang/RuntimeException.html)<br>Represents the exception that occurs during validation of a data |
| [WorkspaceUnavailableException](-workspace-unavailable-exception/index.md) | [jvm]<br>class [WorkspaceUnavailableException](-workspace-unavailable-exception/index.md)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [RuntimeException](https://docs.oracle.com/javase/8/docs/api/java/lang/RuntimeException.html)<br>Exception that occurs if workspace can't be booked |
