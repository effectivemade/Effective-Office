package office.effective.features.booking.repository

import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.exception.MissingIdException
import office.effective.common.exception.WorkspaceUnavailableException
import office.effective.common.utils.UuidValidator
import office.effective.features.booking.converters.BookingRepositoryConverter
import office.effective.features.user.repository.*
import office.effective.model.Booking
import office.effective.model.UserModel
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.*
import java.util.*
import kotlin.collections.List
import office.effective.model.Workspace

/**
 * Class that executes database queries with bookings
 *
 * All WorkspaceRepository methods return [Booking] objects with
 * [Workspace] and [UserModel] objects with empty [Workspace.utilities] and [UserModel.integrations]
 *
 * TODO: should implements IBookingRepository interface (add minStartTime and maxStartTime to queries)
 */
class BookingRepository(private val database: Database,
                        private val converter: BookingRepositoryConverter,
                        private val uuidValidator: UuidValidator){

    /**
     * Returns whether a booking with the given id exists
     *
     * @param id booking id
     * @return true if booking exists
     * @author Daniil Zavyalov
     */
    fun existsById(id: String): Boolean {
        return database.workspaceBooking.count { it.id eq uuidValidator.uuidFromString(id) } > 0
    }

    /**
     * Deletes the booking with the given id
     *
     * If the booking is not found in the database it is silently ignored
     *
     * @param id booking id
     * @author Daniil Zavyalov
     */
    fun deleteById(id: String) {
        val uuid = uuidValidator.uuidFromString(id)
        database.bookingParticipants.removeIf { it.bookingId eq uuid }
        database.workspaceBooking.removeIf { it.id eq uuid }
    }

    /**
     * Retrieves a booking model by its id.
     * Retrieved booking contains user and workspace models without integrations and utilities
     *
     * @param bookingId booking id
     * @return [Booking] with the given [bookingId] or null if booking with the given id doesn't exist
     * @author Daniil Zavyalov
     */
    fun findById(bookingId: String): Booking? {
        val uuid = uuidValidator.uuidFromString(bookingId)
        val entity = database.workspaceBooking.find { it.id eq uuid } ?: return null
        val participants = findParticipants(uuid)
        return entity.let { converter.entityToModel(it, participants) }
    }

    /**
     * Returns all bookings with the given owner id
     *
     * @param ownerId
     * @return List of all user [Booking]
     * @author Daniil Zavyalov
     */
    fun findAllByOwnerId(ownerId: UUID): List<Booking> {
        val entityList = database.workspaceBooking.filter { it.ownerId eq ownerId }.toList()
        return entityList.map {
            val participants = findParticipants(it.id)
            converter.entityToModel(it, participants)
        }
    }

    /**
     * Returns all bookings with the given workspace id
     *
     * @param workspaceId
     * @return List of all workspace [Booking]
     * @author Daniil Zavyalov
     */
    fun findAllByWorkspaceId(workspaceId: UUID): List<Booking> {
        val entityList = database.workspaceBooking.filter { it.workspaceId eq workspaceId }.toList()
        return entityList.map {
            val participants = findParticipants(it.id)
            converter.entityToModel(it, participants)
        }
    }

    /**
     * Returns all bookings with the given workspace and owner id
     *
     * @param ownerId
     * @param workspaceId
     * @return List of all [Booking]s with the given workspace and owner id
     * @author Daniil Zavyalov
     */
    fun findAllByOwnerAndWorkspaceId(ownerId: UUID, workspaceId: UUID): List<Booking> {
        val entityList = database.workspaceBooking
            .filter { it.ownerId eq ownerId }
            .filter { it.workspaceId eq workspaceId }.toList()

        return entityList.map {
            val participants = findParticipants(it.id)
            converter.entityToModel(it, participants)
        }
    }

    /**
     * Retrieves all bookings
     *
     * @return All [Booking]s
     * @author Daniil Zavyalov
     */
    fun findAll(): List<Booking> {
        val entityList = database.workspaceBooking.toList()
        return entityList.map {
            val participants = findParticipants(it.id)
            converter.entityToModel(it, participants)
        }
    }

    /**
     * Retrieves all [users][UserEntity] who participate in the [booking][Booking] with the given id
     *
     * @param bookingId
     * @return booking participants
     * @author Daniil Zavyalov
     */
    private fun findParticipants(bookingId: UUID): List<UserEntity> {
        return database
            .from(BookingParticipants)
            .innerJoin(right = Users, on = BookingParticipants.userId eq Users.id)
            .select()
            .where { BookingParticipants.bookingId eq bookingId }
            .map { row -> Users.createEntity(row) }
    }

    /**
     * Checks whether the workspace is available for booking for the given period
     *
     * @param bookingEntity
     * @return true if workspace can be booked in period from [Booking.beginBooking] to [Booking.endBooking]
     * @author Daniil Zavyalov
     */
    private fun workspaceAvailableForBooking(bookingEntity: WorkspaceBookingEntity): Boolean {
        return database.workspaceBooking.none {
            (it.workspaceId eq bookingEntity.workspace.id) and (it.beginBooking lt bookingEntity.endBooking) and (it.endBooking gt bookingEntity.beginBooking)
        }
    }

    /**
     * Saves a given booking. If given model will have an id, it will be ignored.
     * Use the returned model for further operations
     *
     * @param booking [Booking] to be saved
     * @return saved [Booking]
     * @throws WorkspaceUnavailableException if workspace can't be booked in a given period
     * @author Daniil Zavyalov
     */
    fun save(booking: Booking): Booking {
        val id = UUID.randomUUID()
        booking.id = id.toString()
        val entity = converter.modelToEntity(booking)

        if (!workspaceAvailableForBooking(entity))
            throw WorkspaceUnavailableException("Workspace with id ${entity.workspace.id} " +
                    "unavailable at time between ${entity.beginBooking} and ${entity.endBooking}")

        database.workspaceBooking.add(converter.modelToEntity(booking))

        val participantList = findParticipantEntities(booking.participants)
        for(participant in participantList) {
            database.insert(BookingParticipants) {
                set(it.bookingId, entity.id)
                set(it.userId, participant.id)
            }
        }
        return booking
    }

    /**
     * Checks whether the booking can be updated with the given booking period
     *
     * @param entity
     * @return true if workspace can be booked in period from [Booking.beginBooking] to [Booking.endBooking]
     * @author Daniil Zavyalov
     */
    private fun bookingCanBeUpdated(entity: WorkspaceBookingEntity): Boolean {
        return database.workspaceBooking.none {
            (it.id neq entity.id) and (it.workspaceId eq entity.workspace.id) and (it.beginBooking lt entity.endBooking) and (it.endBooking gt entity.beginBooking)
        }
    }

    /**
     * Updates a given booking. Use the returned model for further operations
     *
     * @param booking changed booking
     * @return [Booking] after change saving
     * @throws InstanceNotFoundException if booking given id doesn't exist in the database
     * @throws WorkspaceUnavailableException if workspace can't be booked in a given period
     * @author Daniil Zavyalov
     */
    fun update(booking: Booking): Booking {
        booking.id?.let {
            if(!existsById(it))
                throw InstanceNotFoundException(WorkspaceBookingEntity::class, "Booking with id $it not wound")
        }

        val entity = converter.modelToEntity(booking)

        if (!bookingCanBeUpdated(entity))
            throw WorkspaceUnavailableException("Workspace with id ${entity.workspace.id} " +
                    "unavailable at time between ${entity.beginBooking} and ${entity.endBooking}")

        database.workspaceBooking.update(entity)

        database.bookingParticipants.removeIf { it.bookingId eq entity.id }
        saveParticipants(booking.participants, entity.id)
        return booking
    }

    /**
     * Adds many-to-many relationship between booking and its participants.
     * Uses [UserModel.id] to find actual [participant entity][UserEntity].
     *
     * @param participantModels
     * @return actual participant entities
     * @author Daniil Zavyalov
     */
    private fun saveParticipants(participantModels: List<UserModel>, bookingId: UUID): List<UserEntity> {
        val participantList = findParticipantEntities(participantModels)
        for(participant in participantList) {
            database.insert(BookingParticipants) {
                set(it.bookingId, bookingId)
                set(it.userId, participant.id)
            }
        }
        return participantList
    }

    /**
     * Retrieves user entities by the ids of the given user models
     * Uses [UserModel.id] to find actual [participant entity][UserEntity].
     *
     * @param participantModels
     * @return actual participant entities
     * @author Daniil Zavyalov
     */
    private fun findParticipantEntities(participantModels: List<UserModel>): List<UserEntity> {
        val participantList = mutableListOf<UserEntity>()

        for (participant in participantModels) {
            val participantId: UUID = participant.id
                ?: throw MissingIdException("User with name ${ participant.fullName } doesn't have an id")

            val user: UserEntity = database.users.find { it.id eq participantId }
                ?: throw InstanceNotFoundException(UserEntity::class, "User with id $participantId not found", participantId)
            participantList.add(user)
        }
        return participantList
    }
}