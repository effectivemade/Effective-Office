package office.effective.features.workspace.DI

import office.effective.features.workspace.converters.WorkspaceFacadeConverter
import office.effective.features.workspace.converters.WorkspaceRepositoryConverter
import office.effective.features.workspace.facade.WorkspaceFacade
import office.effective.features.workspace.repository.WorkspaceRepository
import office.effective.features.workspace.service.WorkspaceService
import office.effective.serviceapi.IWorkspaceService
import org.koin.dsl.module

val workspaceDiModule = module(createdAtStart = true) {
    single { WorkspaceRepositoryConverter(get()) }
    single { WorkspaceRepository(get(), get()) }
    single { WorkspaceService(get()) }
    single<IWorkspaceService> { get<WorkspaceService>() }
    single { WorkspaceFacadeConverter(get()) }
    single { WorkspaceFacade(get(), get(), get(), get()) }
}
