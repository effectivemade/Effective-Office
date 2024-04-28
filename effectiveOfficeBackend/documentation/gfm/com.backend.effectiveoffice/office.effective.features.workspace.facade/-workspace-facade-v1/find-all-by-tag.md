//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.workspace.facade](../index.md)/[WorkspaceFacadeV1](index.md)/[findAllByTag](find-all-by-tag.md)

# findAllByTag

[jvm]\
fun [findAllByTag](find-all-by-tag.md)(tag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), withBookingsFrom: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null, withBookingsUntil: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md)&gt;

Returns all [WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md) with their bookings by tag

#### Return

List of [WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md) with their bookings

#### Author

Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| tag | tag name of requested workspaces |
| withBookingsFrom | lower bound (exclusive) for a booking.endBooking to filter by. |
| withBookingsUntil | upper bound (exclusive) for a booking.beginBooking to filter by. |
