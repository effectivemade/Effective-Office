package office.effective.features.auth.di

import office.effective.features.auth.repository.AuthRepository
import office.effective.features.auth.service.*
import org.koin.dsl.module

val authDiModule = module(createdAtStart = true) {
    single { AuthRepository(get()) }
    single {
        AuthorizationPipeline()
            .addAuthorizer(PermitAuthorizer(listOf("/swagger/", "/notifications" )))
            .addAuthorizer(TokenAuthorizer(get(), get()))
            .addAuthorizer(RequestAuthorizer(get()))
            .addAuthorizer(ApiKeyAuthorizer())
    }
    single { TokenExtractor() }

}
