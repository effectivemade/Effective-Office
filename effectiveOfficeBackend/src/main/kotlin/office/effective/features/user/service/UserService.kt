package office.effective.features.user.service

import office.effective.features.user.repository.UserRepository
import office.effective.model.UserModel
import office.effective.serviceapi.IUserService
import java.util.*

/**
 * Class that provides methods to manipulate [UserModel] objects
 * */
class UserService(private val repository: UserRepository) : IUserService {

    override fun getUsersByTag(tagStr: String): Set<UserModel> {
        return repository.findByTag(repository.findTagByName(tagStr).id)
    }

    /**
     * Retrieves all users
     * @return [Set]<[UserModel]>
     * */
    override fun getAll(): Set<UserModel> {
        return repository.findAll()
    }

    override fun getUserById(userIdStr: String): UserModel? {
        return repository.findById(UUID.fromString(userIdStr))
    }

    /**
     * Updates a given user. Use the returned model for further operations
     *
     * @param user User's model
     * @return [UserModel]
     */
    override fun updateUser(user: UserModel): UserModel {
        return repository.updateUser(user)
    }

    /**
     * Return user with specified email
     *
     * @param emailStr user email
     * @return [UserModel]
     * */
    override fun getUserByEmail(emailStr: String): UserModel? {
        return repository.findByEmail(emailStr)
    }
}