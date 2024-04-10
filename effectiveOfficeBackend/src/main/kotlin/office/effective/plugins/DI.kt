package office.effective.plugins

import io.ktor.server.application.*
import office.effective.common.notifications.firebaseDiModule
import office.effective.common.di.commonDiModule
import office.effective.features.booking.di.bookingDiModule
import office.effective.common.di.calendarDiModule
import office.effective.features.auth.di.authDiModule
import office.effective.features.user.di.userDIModule
import office.effective.features.workspace.DI.workspaceDiModule
import org.koin.ktor.plugin.Koin

/**
 * Koin dependency injection modules
 */
fun Application.configureDI() {
    install(Koin) {
        modules(
            commonDiModule,
            userDIModule,
            workspaceDiModule,
            bookingDiModule,
            authDiModule,
            calendarDiModule,
            firebaseDiModule
        )
    }
}
