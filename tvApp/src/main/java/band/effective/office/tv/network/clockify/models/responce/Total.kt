package band.effective.office.tv.network.clockify.models.responce

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Total(
    @Json(name = "_id")
    val id: String,
    val amounts: List<Any?>?,
    val entriesCount: Int,
    val numOfCurrencies: Int,
    val totalAmount: Any?,
    val totalAmountByCurrency: TotalAmountByCurrency,
    val totalBillableTime: Int,
    val totalTime: Int
)