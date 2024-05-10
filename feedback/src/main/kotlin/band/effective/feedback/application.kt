import band.effective.feedback.data.mattermost.MattermostApi
import band.effective.feedback.utils.KtorClientBuilder

suspend fun main() {
    val mattermostApi = MattermostApi(
        client = KtorClientBuilder { setToken(System.getenv("MattermostBotKey")) },
        baseUrl = System.getenv("MattermostServer")
    )
    val max = mattermostApi.getUserInfo("maxim.mishchenko")
    val bot = mattermostApi.me()
    val channel = mattermostApi.getChanel("${bot.id}__${max.id}", "effectiveband")
    val message = mattermostApi.writeMessage(channel.id, "Hello from kotlin")
    val reaction = mattermostApi.makeReaction(bot.id, message.id, "eyes")
    println(reaction)
    println("hello world")
}