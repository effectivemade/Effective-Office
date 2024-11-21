package office.effective.features.user.converters

import office.effective.common.exception.MissingIdException
import office.effective.features.user.repository.UserEntity
import office.effective.model.IntegrationModel
import office.effective.model.UserModel

/**
 * Converters between [UserModel] and [UserEntity]
 * */
class UserModelEntityConverter {

    /**
     * Converts [UserModel] to [UserEntity]
     *
     * @throws MissingIdException if the user id is null
     * @param userModel [UserModel] to be converted
     * @return converted [UserEntity]
     *
     * @author Danil Kiselev, Daniil Zavyalov
     */
    fun modelToEntity(userModel: UserModel): UserEntity {
        return UserEntity {
            id = userModel.id ?: throw MissingIdException("User with name ${userModel.fullName} doesn't have an id")
            fullName = userModel.fullName
            tag = userModel.tag!!
            active = userModel.active
            role = userModel.role
            avatarURL = userModel.avatarURL
            email = userModel.email
        }
    }

    /**
     * Converts [UserModel] to [UserEntity]
     *
     * @param userEntity [UserEntity] to be converted
     * @param integrations [Set]<[IntegrationModel]>? to add into user model
     * @return converted [UserModel]
     *
     * @author Danil Kiselev, Daniil Zavyalov
     */
    fun entityToModel(userEntity: UserEntity, integrations: Set<IntegrationModel>?): UserModel {
        return UserModel(
            fullName = userEntity.fullName,
            id = userEntity.id,
            tag = userEntity.tag,
            active = userEntity.active,
            role = userEntity.role,
            avatarURL = userEntity.avatarURL,
            integrations = integrations,
            email = userEntity.email
        )

    }
}