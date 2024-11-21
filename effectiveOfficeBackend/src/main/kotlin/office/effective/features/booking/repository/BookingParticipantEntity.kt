package office.effective.features.booking.repository

import office.effective.features.user.repository.UserEntity
import office.effective.features.user.repository.Users
import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.uuid

interface BookingParticipantEntity: Entity<BookingParticipantEntity> {
    companion object : Entity.Factory<BookingParticipantEntity>()
    var user: UserEntity
    var booking: WorkspaceBookingEntity
}

object BookingParticipants: Table<BookingParticipantEntity>("booking_participants") {
    val userId = uuid("user_id").primaryKey().references(Users) { it.user }
    val bookingId = uuid("booking_id").primaryKey().references(WorkspaceBooking) { it.booking }
}

val Database.bookingParticipants get() = this.sequenceOf(BookingParticipants)
