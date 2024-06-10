package office.effective.features.booking.converters

import model.RecurrenceDTO
import office.effective.common.constants.BookingConstants
import office.effective.model.Ending
import office.effective.model.Recurrence
import office.effective.model.RecurrenceModel
import java.text.SimpleDateFormat
import java.util.*

//TODO: all converters should be classes
/**
 * Converts between [RecurrenceDTO] and [RecurrenceModel] objects
 */
object RecurrenceConverter {

    /**
     * Converts [RecurrenceModel] to [RecurrenceDTO]
     *
     * @param model [RecurrenceModel] to be converted
     * @return The resulting [RecurrenceDTO] object
     */
    fun modelToDto(model: RecurrenceModel): RecurrenceDTO {
        return RecurrenceDTO(
            model.interval,
            model.freq,
            model.count,
            model.until,
            model.byDay,
            model.byMonth,
            model.byYearDay,
            model.byHour
        )
    }

    /**
     * Converts [RecurrenceDTO] to [RecurrenceModel]
     *
     * @param dto [RecurrenceDTO] to be converted
     * @return The resulting [RecurrenceModel] object
     */
    fun dtoToModel(dto: RecurrenceDTO): RecurrenceModel {
        if (dto.count != null && dto.until != null)
            throw IllegalArgumentException("You can use either COUNT or UNTIL to specify the end of the event recurrence. Don't use both in the same rule.")
        return RecurrenceModel(
            dto.interval, dto.freq, dto.count, dto.until, dto.byDay, dto.byMonth, dto.byYearDay, dto.byHour
        )
    }

    fun recurrenceToModel(recurrence: Recurrence): RecurrenceModel {
        return RecurrenceModel(
            interval =recurrence.interval,
            freq =recurrence.freq.toString(),
            count = if (recurrence.ending is Ending.Count) recurrence.ending.value else null,
            until = if (recurrence.ending is Ending.Until) parseUntil(recurrence.ending.value) else null,
            byDay = recurrence.byDay,
            byMonth = recurrence.byMonth,
            byYearDay = recurrence.byYearDay,
            byHour = recurrence.byHour
        )
    }

    private fun parseUntil(untilStr: String): Long {
        val date: Date = SimpleDateFormat(BookingConstants.UNTIL_FORMAT).parse(untilStr)
        println("UNTIL: $untilStr")
        return date.time
    }
}