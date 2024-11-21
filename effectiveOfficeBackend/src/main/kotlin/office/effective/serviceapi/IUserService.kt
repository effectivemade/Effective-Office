package office.effective.serviceapi

import office.effective.model.UserModel

/**
 * Interface that provides methods to manipulate [UserModel] objects
 * */
interface IUserService {

    /**
     * Return all users with given tag
     *
     * @param tagStr name of the tag
     * @return Set<UserModel>
     * @author Kiselev Danil
     * */
    fun getUsersByTag(tagStr: String): Set<UserModel>;

    /**
     * Return all users in database
     *
     * @return Set<UserModel>
     * @author Daniil Zavyalov
     * */
    fun getAll(): Set<UserModel>

    /**
     * Return user by id, or returns null, if user not found
     *
     * @param userIdStr string value of user id
     * @return UserModel or null
     * @author Kiselev Danil
     * */
    fun getUserById(userIdStr: String): UserModel?;

    /**
     * Change existed user in database
     *
     * @param user User's model
     * @return UserModel
     * @author Kiselev Danil
     * */
    fun updateUser(user: UserModel): UserModel;

    /**
     * Return user with specified email
     *
     * @param emailStr user email
     * @return UserModel
     * @author Kiselev Danil
     * */
    fun getUserByEmail(emailStr: String): UserModel?;
}