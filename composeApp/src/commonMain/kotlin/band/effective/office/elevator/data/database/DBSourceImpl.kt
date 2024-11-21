package band.effective.office.elevator.data.database

import band.effective.office.elevator.Database
import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.domain.models.toUser

class DBSourceImpl(
    val database: Database
) : DBSource {

    private val profileQueries = database.profileQueries
    override suspend fun getCurrentUserInfo(): User? {
        val profile = profileQueries.selectUser().executeAsOneOrNull()
        return profile?.toUser()
    }

    override suspend fun update(user: User, idToken: String) {
        with(user) {
            profileQueries.updateUser(
                id = id,
                name = userName,
                post = post,
                email = email,
                phoneNumber = phoneNumber,
                telegramNick = telegram,
                imageUrl = imageUrl
            )
        }
        profileQueries.updateIdToken(idToken = idToken)
    }

    override suspend fun update(user: User) {
        with(user) {
            profileQueries.updateUser(
                id = id,
                name = userName,
                post = post,
                email = email,
                phoneNumber = phoneNumber,
                telegramNick = telegram,
                imageUrl = imageUrl
            )
        }
    }

    override fun deleteUserData() {
        profileQueries.deleteUser()
        profileQueries.deleteToken()
    }
}