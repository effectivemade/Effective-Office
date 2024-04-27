//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.user.repository](../index.md)/[UserRepository](index.md)/[findAllByEmail](find-all-by-email.md)

# findAllByEmail

[jvm]\
fun [findAllByEmail](find-all-by-email.md)(email: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UserModel](../../office.effective.model/-user-model/index.md)&gt;

Retrieves a user models by email list

#### Return

[UserModel](../../office.effective.model/-user-model/index.md)

#### Author

Daniil Zavyalov

#### Throws

| | |
|---|---|
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | if user with the given email doesn't exist in database |
