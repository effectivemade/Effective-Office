package band.effective.office.elevator.domain.di

import band.effective.office.elevator.data.repository.ProfileRepositoryImpl
import band.effective.office.elevator.data.repository.UserRepositoryImpl
import band.effective.office.elevator.domain.repository.ProfileRepository
import band.effective.office.elevator.domain.repository.UserRepository
import band.effective.office.elevator.domain.useCase.GetLastUserIdUseCase
import band.effective.office.elevator.domain.useCase.GetUserByIdUseCase
import band.effective.office.elevator.domain.useCase.GetUserUseCase
import band.effective.office.elevator.domain.useCase.UpdateUserUseCase
import org.koin.dsl.module

internal val profileDomainModuleDI = module {
    single<ProfileRepository> {
        ProfileRepositoryImpl(
            api = get(), bdSource = get()
        )
    }
    single { GetUserByIdUseCase(get()) }
    single { UpdateUserUseCase(get()) }
    single {GetUserUseCase(
        profileRepository = get()
    )
    }
}