package band.effective.office.tvServer.di

import band.effective.office.tvServer.api.duolingo.DuolingoApi
import band.effective.office.tvServer.api.duolingo.DuolingoApiImpl
import band.effective.office.tvServer.api.leader.LeaderApi
import band.effective.office.tvServer.api.leader.LeaderApiImpl
import band.effective.office.tvServer.api.mattermost.MattermostApi
import band.effective.office.tvServer.api.mattermost.MattermostApiImpl
import band.effective.office.tvServer.utils.defaultHttpClient
import band.effective.office.tvServer.utils.firebase
import band.effective.office.workTogether.WorkTogether
import band.effective.office.workTogether.WorkTogetherImpl
import com.google.firebase.messaging.FirebaseMessaging
import notion.api.v1.NotionClient
import org.koin.dsl.module

val dataModule = module {
    single<LeaderApi> { LeaderApiImpl(defaultHttpClient()) }
    single<MattermostApi> { MattermostApiImpl(defaultHttpClient(System.getenv("APIKEY") ?: "")) }
    single<FirebaseMessaging> { firebase() }
    single<WorkTogether> {
        WorkTogetherImpl(
            notionClient = NotionClient(System.getenv("NOTION_TOKEN")),
            notionDatabaseId = System.getenv("NOTION_DATABASE_ID")
        )
    }
    single<DuolingoApi> { DuolingoApiImpl(defaultHttpClient()) }
}