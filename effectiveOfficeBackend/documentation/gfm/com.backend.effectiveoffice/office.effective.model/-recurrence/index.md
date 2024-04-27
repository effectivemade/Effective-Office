//[com.backend.effectiveoffice](../../../index.md)/[office.effective.model](../index.md)/[Recurrence](index.md)

# Recurrence

[jvm]\
data class [Recurrence](index.md)(val interval: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val freq: [Freq](../-freq/index.md), val ending: [Ending](../-ending/index.md), val byDay: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt;, val byMonth: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt;, val byYearDay: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt;, val byHour: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt;)

## Constructors

| | |
|---|---|
| [Recurrence](-recurrence.md) | [jvm]<br>constructor(interval: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), freq: [Freq](../-freq/index.md), ending: [Ending](../-ending/index.md), byDay: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt;, byMonth: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt;, byYearDay: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt;, byHour: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt;) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [byDay](by-day.md) | [jvm]<br>val [byDay](by-day.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt; |
| [byHour](by-hour.md) | [jvm]<br>val [byHour](by-hour.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt; |
| [byMonth](by-month.md) | [jvm]<br>val [byMonth](by-month.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt; |
| [byYearDay](by-year-day.md) | [jvm]<br>val [byYearDay](by-year-day.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt; |
| [ending](ending.md) | [jvm]<br>val [ending](ending.md): [Ending](../-ending/index.md) |
| [freq](freq.md) | [jvm]<br>val [freq](freq.md): [Freq](../-freq/index.md) |
| [interval](interval.md) | [jvm]<br>val [interval](interval.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

## Functions

| Name | Summary |
|---|---|
| [rule](../../office.effective.features.booking.converters/-recurrence-rule-factory/rule.md) | [jvm]<br>fun [Recurrence](index.md).[rule](../../office.effective.features.booking.converters/-recurrence-rule-factory/rule.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [toDto](to-dto.md) | [jvm]<br>fun [toDto](to-dto.md)(): [RecurrenceDTO](../../model/-recurrence-d-t-o/index.md) |
