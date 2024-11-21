//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.user.repository](../index.md)/[UserRepository](index.md)/[findByEmail](find-by-email.md)

# findByEmail

[jvm]\
fun [findByEmail](find-by-email.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [UserModel](../../office.effective.model/-user-model/index.md)?

Retrieves a user model by email

#### Return

[UserModel](../../office.effective.model/-user-model/index.md)

#### Author

Daniil Zavyalov

#### Throws

| | |
|---|---|
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | if user with the given email doesn't exist in database |
