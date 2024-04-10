package office.effective.features.user.facade

import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.utils.DatabaseTransactionManager
import office.effective.features.user.converters.UserDTOModelConverter
import office.effective.dto.UserDTO
import office.effective.serviceapi.IUserService
import office.effective.model.UserModel

/**
 * Class used in routes to handle user-related requests.
 * Provides business transaction, data conversion and validation.
 * */
class UserFacade(
    private val service: IUserService,
    private val converterDTO: UserDTOModelConverter,
    private val transactionManager: DatabaseTransactionManager
) {
    /**
     * Return all users with given tag
     *
     * @param tagStr name of the tag
     * @return [Set]<[UserDTO]>
     * @author Kiselev Danil, Daniil Zavyalov
     * */
    fun getUsersByTag(tagStr: String): Set<UserDTO> {
        val models: Set<UserModel> =
            transactionManager.useTransaction<Set<UserModel>>({ service.getUsersByTag(tagStr) })
        val dtos: MutableSet<UserDTO> = mutableSetOf()
        models.forEach { dtos.add(converterDTO.modelToDTO(it)) }
        return dtos.toSet()
    }

    /**
     * Retrieves all users
     * @return [Set]<[UserDTO]>
     * @throws InstanceNotFoundException(UserModel::class, "User with id ${userIdStr} not found", null)
     *
     * @author Daniil Zavyalov
     * */
    fun getUsers(): Set<UserDTO> {
        return transactionManager.useTransaction({
            val dtos: MutableSet<UserDTO> = mutableSetOf()
            for (model in service.getAll()) {
                dtos.add(converterDTO.modelToDTO(model))
            }
            dtos.toSet()
        })
    }

    /**
     * Return user by id, or returns null, if user not found
     *
     * @param userIdStr string value of user id
     * @return [UserDTO]
     * @throws InstanceNotFoundException(UserModel::class, "User with id ${userIdStr} not found", null)
     * @author Kiselev Danil
     * */
    fun getUserById(userIdStr: String): UserDTO {
        return transactionManager.useTransaction<UserDTO>({
            converterDTO.modelToDTO(
                service.getUserById(userIdStr) ?: throw InstanceNotFoundException(
                    UserModel::class, "User with id ${userIdStr} not found", null
                )
            )
        })
    }

    /**
     * Return user by email
     *
     * @param email user email
     * @return [UserDTO]
     * @throws InstanceNotFoundException(UserModel::class, "User with email $email not found", null)
     * @author Kiselev Danil, Daniil Zavyalov
     * */
    fun getUserByEmail(email: String): UserDTO {
        return transactionManager.useTransaction<UserDTO>({
            converterDTO.modelToDTO(
                service.getUserByEmail(email) ?: throw InstanceNotFoundException(
                    UserModel::class, "User with email $email not found", null
                )
            )
        })
    }

    /**
     * Updates a given user. Use the returned model for further operations
     *
     * @param user [UserDTO] - user dto with changed information
     * @return [UserDTO] - updated user
     *
     * @author Danil Kiselev, Daniil Zavyalov
     */
    fun updateUser(user: UserDTO): UserDTO {
        return transactionManager.useTransaction({
            converterDTO.modelToDTO(
                service.updateUser(
                    converterDTO.dTOToModel(user)
                )
            )
        })
    }
}
