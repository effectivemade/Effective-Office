import band.effective.feedback.utils.Env
import band.effective.feedback.utils.createSessionRetryContext
import band.effective.feedback.utils.getResultQuery

suspend fun main() {
    createSessionRetryContext(Env.ydbKeyJson, Env.ydbConnectionString).getResultQuery("select * from Requests") {
        getColumn("name").text
    }.let { println(it) }
    println("hello world")
}