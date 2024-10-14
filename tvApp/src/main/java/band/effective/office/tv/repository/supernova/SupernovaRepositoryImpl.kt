package band.effective.office.tv.repository.supernova

import band.effective.office.tv.BuildConfig
import band.effective.office.tv.repository.workTogether.getNumberFromProp
import band.effective.office.tv.repository.workTogether.getStringFromProp
import notion.api.v1.NotionClient
import notion.api.v1.request.databases.QueryDatabaseRequest
import javax.inject.Inject

class SupernovaRepositoryImpl @Inject constructor(
    private val notionClient: NotionClient
) : SupernovaRepository {
    override fun getTalents(): List<Talent> {
        return notionClient.queryDatabase(
            request = QueryDatabaseRequest(BuildConfig.supernovaDatabaseId)
        ).results
            .groupBy { it.getStringFromProp("Talent") }
            .map { (id, page) ->
                Talent(
                    id = id ?: "",
                    score = page.sumOf { it.getNumberFromProp("Number") ?: 0 }
                )
            }
    }
}