package band.effective.office.tv.utils

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.util.Calendar
import java.util.GregorianCalendar

class GregorianCalendarMoshiAdapter : JsonAdapter<GregorianCalendar>() {
    override fun fromJson(reader: JsonReader): GregorianCalendar? {
        val components = reader.readJsonValue().toString().split("-")
        if (components.size != 5) return null
        return GregorianCalendar(
            components[0].toInt(),
            components[1].toInt(),
            components[2].toInt(),
            components[3].toInt(),
            components[4].toInt()
        )
    }

    override fun toJson(writer: JsonWriter, value: GregorianCalendar?) {
        if (value != null) {
            writer.jsonValue(
                "${value.get(Calendar.YEAR)}" +
                        "-${value.get(Calendar.MONTH)}" +
                        "-${value.get(Calendar.DAY_OF_MONTH)}" +
                        "-${value.get(Calendar.HOUR)}" +
                        "-${value.get(Calendar.MINUTE)}"
            )
        } else {
            writer.jsonValue(null)
        }
    }
}