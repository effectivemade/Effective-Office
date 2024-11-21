package band.effective.office.tablet.di

import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val commonModule = module {
    loadKoinModules(listOf(networkModule, domainModule, uiModule))
}