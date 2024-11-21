//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.user.repository](../index.md)/[UserRepository](index.md)/[findTagByName](find-tag-by-name.md)

# findTagByName

[jvm]\
fun [findTagByName](find-tag-by-name.md)(tagName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [UserTagModel](../../office.effective.model/-user-tag-model/index.md)

Returns [UserTagModel](../../office.effective.model/-user-tag-model/index.md) by tag name

#### Return

[UserTagModel](../../office.effective.model/-user-tag-model/index.md)

#### Author

Danil Kiselev

#### Parameters

jvm

| | |
|---|---|
| tagName | name of the tag |

#### Throws

| | |
|---|---|
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | ([UsersTagEntity](../-users-tag-entity/index.md)::class, &quot;Tag with name ${tagName} not found&quot;) |
