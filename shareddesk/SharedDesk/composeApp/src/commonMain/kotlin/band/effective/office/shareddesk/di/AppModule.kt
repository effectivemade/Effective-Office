package band.effective.office.shareddesk.di

import band.effective.office.shareddesk.data.di.dataModule
import band.effective.office.shareddesk.domain.di.domainModule
import band.effective.office.shareddesk.interactivemap.di.interactiveMapModule
import band.effective.office.svgparser.di.svgParserModule

fun appModule() = listOf(interactiveMapModule, domainModule, svgParserModule, dataModule)