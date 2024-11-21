package band.effective.office.elevator.domain.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
class UserData(
    var phoneNumber: String = "9136476225",
    var post: String? = "Android developer",
    var name: String = "Слава",
    var telegramNick: String = "Sl1vka",
    var email: String = "sl1vka.run@gmail.com",
    val imageUrl: String? = ""
) : Parcelable