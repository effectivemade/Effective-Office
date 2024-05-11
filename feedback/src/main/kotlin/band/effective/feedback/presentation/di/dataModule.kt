package band.effective.feedback.presentation.di

import band.effective.feedback.data.mattermost.MattermostApi
import band.effective.feedback.utils.Env
import band.effective.feedback.utils.KtorClientBuilder
import band.effective.feedback.utils.createSessionRetryContext
import io.ktor.client.*
import org.koin.dsl.module

fun dataModule() = module {
    factory<HttpClient> { KtorClientBuilder { setToken(Env.mattermostBotKey) } }
    factory { MattermostApi(client = get(), baseUrl = Env.mattermostServer) }
    factory {
        createSessionRetryContext(
            authorizedKeyJson = Env.ydbKeyJson,
            connectionString = Env.ydbConnectionString
        )
    }
}