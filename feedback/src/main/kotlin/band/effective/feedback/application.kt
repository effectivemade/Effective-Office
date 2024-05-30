import band.effective.feedback.presentation.modules
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(factory = Netty, host = "0.0.0.0", port = 8080, module = Application::modules).start(wait = true)
}