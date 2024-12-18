package band.effective.office.shareddesk.interactivemap.di

import band.effective.office.shareddesk.interactivemap.presentation.AdminOfficeMapViewModel
import band.effective.office.shareddesk.interactivemap.presentation.OfficeMapViewerViewModel
import band.effective.office.shareddesk.interactivemap.presentation.mapper.CanvasDataUiMapper
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val interactiveMapModule = module {
    single { CanvasDataUiMapper() }
    viewModel<AdminOfficeMapViewModel> { AdminOfficeMapViewModel(get(), get(), get()) }
    viewModel<OfficeMapViewerViewModel> { OfficeMapViewerViewModel(get(), get(), get()) }
}