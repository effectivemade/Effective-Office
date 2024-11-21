package band.effective.office.tv.utils

import android.content.Context

class RStringGetter constructor(private val context: Context) {
    fun getString(stringId: Int): String = context.getString(stringId)
}