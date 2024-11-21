package band.effective.office.tablet.domain.model

import android.content.Context
import android.content.SharedPreferences

class Settings(private val nameCurrentRoom: String) {
    companion object {
        var current = Settings(SharedPref.sharedPref.getRoom() ?: "")
    }

    private fun getRoom() = current.nameCurrentRoom

    fun checkCurrentRoom() = current.getRoom()

    fun updateSettings(newNameCurrentRoom: String) {
        current = Settings(nameCurrentRoom = newNameCurrentRoom)
        SharedPref.sharedPref.updateRoom(newNameCurrentRoom)
    }

    fun removeNameRoom() {
        SharedPref.sharedPref.removeNameRoom()
        current = Settings("")
    }

}

class SharedPref{
    companion object {
        const val NAME_SHARED_PREF = "settings"
        const val NAME_ROOM = "nameRoom"
        val sharedPref = SharedPref()
    }

    private lateinit var sharedPref: SharedPreferences

    fun init(context: Context){
        sharedPref = context.getSharedPreferences(NAME_SHARED_PREF, Context.MODE_PRIVATE)
    }

    fun getRoom() = sharedPref.getString(NAME_ROOM, "")

    fun updateRoom(nameRoom: String) {
        sharedPref.edit().putString(NAME_ROOM, nameRoom).commit()
    }

    fun removeNameRoom() = sharedPref.edit().remove(NAME_ROOM).commit()
}


