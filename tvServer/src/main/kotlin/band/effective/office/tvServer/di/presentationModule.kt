package band.effective.office.tvServer.di

import band.effective.office.tvServer.repository.message.MessageRepository
import band.effective.office.tvServer.service.duolingo.DuolingoService
import band.effective.office.tvServer.service.event.EventService
import band.effective.office.tvServer.service.leaderId.LeaderIdService
import band.effective.office.tvServer.service.mattermost.MattermostService
import org.koin.core.qualifier.named
import org.koin.dsl.module

val presentationModule = module {
    single<LeaderIdService> { LeaderIdService(leaderRepository = get()) }
    single<MattermostService> {
        MattermostService(
            mattermostRepository = get(),
            simpleMessageRepository = get<MessageRepository>(qualifier = named("simple")),
            importantMessageRepository = get<MessageRepository>(qualifier = named("important")),
            postRepository = get()
        )
    }
    single<EventService> { EventService(get()) }
    single<DuolingoService> { DuolingoService(get()) }
}