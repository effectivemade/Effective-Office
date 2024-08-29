package band.effective.office.tv.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource


sealed class StringResource {
    data class DynamicResource(val value: String): StringResource()
    class AndroidResource(
        val id: Int,
        vararg val args: Any
    ): StringResource()

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicResource -> value
            is AndroidResource -> stringResource(id = id)
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is DynamicResource -> value
            is AndroidResource -> context.getString(id)
        }
    }
}