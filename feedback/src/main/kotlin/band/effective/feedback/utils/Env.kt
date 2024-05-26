package band.effective.feedback.utils

import kotlin.reflect.KProperty

object Env {

    val mattermostBotKey by EnvGetter("MattermostBotKey")
    val mattermostServer by EnvGetter("MattermostServer")
    val ydbKeyJson by EnvGetter("YdbKeyJson")
    val ydbConnectionString by EnvGetter("YdbConnectionString")
    val myUrl by EnvGetter("URL")


    private class EnvGetter(private val key: String) {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
            return System.getenv(key) ?: ""
        }
    }
}