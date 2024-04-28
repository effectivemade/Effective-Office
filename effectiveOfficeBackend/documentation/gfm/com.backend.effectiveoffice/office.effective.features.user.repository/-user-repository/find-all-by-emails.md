//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.user.repository](../index.md)/[UserRepository](index.md)/[findAllByEmails](find-all-by-emails.md)

# findAllByEmails

[jvm]\
fun [findAllByEmails](find-all-by-emails.md)(emails: [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[UserModel](../../office.effective.model/-user-model/index.md)&gt;

Retrieves users models with integrations by emails. If a user with one of the specified emails does not exist, that email will be ignored.

#### Return

users with integrations

#### Author

Daniil Zavyalov
