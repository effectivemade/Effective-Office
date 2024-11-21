package band.effective.office.elevator.di

import band.effective.office.elevator.data.di.dataModuleDI
import band.effective.office.elevator.domain.di.domainModuleDI
import band.effective.office.elevator.domain.di.profileDomainModuleDI
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val appModuleDI = module {
    loadKoinModules(
        listOf(
            dataModuleDI,
            domainModuleDI,
            profileDomainModuleDI
        )
    )
}

