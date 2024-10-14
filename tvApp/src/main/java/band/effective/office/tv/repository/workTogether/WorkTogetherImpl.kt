package band.effective.office.tv.repository.workTogether

import band.effective.office.tv.BuildConfig
import notion.api.v1.NotionClient
import notion.api.v1.model.common.File
import notion.api.v1.model.common.PropertyType
import notion.api.v1.model.pages.Page
import notion.api.v1.request.databases.QueryDatabaseRequest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.GregorianCalendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkTogetherImpl @Inject constructor(private val notionClient: NotionClient) : WorkTogether {
    override fun getAll(): List<Teammate> {
        return notionClient.queryDatabase(
            request = QueryDatabaseRequest(BuildConfig.notionDatabaseId)
        ).results.map { it.toTeammate() }
    }

    override fun getProperty(name: String): Map<String, String?> {
        return notionClient.queryDatabase(
            request = QueryDatabaseRequest(BuildConfig.notionDatabaseId)
        ).results.associate { it.id to it.getStringFromProp(name) }
    }

    private fun Page.toTeammate() =
        Teammate(
            id = id,
            name = getStringFromProp("Name") ?: "Null name",
            positions = getStringFromProp("Position")?.split(" ") ?: listOf(),
            employment = getStringFromProp("Employment") ?: "Null employment",
            startDate = getDateFromProp("Start Date"),
            nextBDay = getDateFromProp("Next B-DAY"),
            workEmail = getStringFromProp("Effective Email"),
            personalEmail = getStringFromProp("Personal Email") ?: "",
            duolingo = getStringFromProp("Профиль Duolingo"),
            photo = getIconUrl() ?: "",
            status = getStringFromProp("Status") ?: "Empty status"
        )
    }

fun Page.getStringFromProp(propName: String) =
    properties[propName]?.run {
        when (type) {
            PropertyType.Title -> title?.firstOrNull()?.text?.content
            PropertyType.RichText -> richText?.firstOrNull()?.text?.content
            PropertyType.MultiSelect -> multiSelect?.fold("") { acc, option -> "$acc ${option.name}" }
            PropertyType.Select -> select?.name
            PropertyType.Date -> date?.start
            PropertyType.Email -> email
            PropertyType.Relation -> relation?.firstOrNull()?.id
            else -> null
        }
    }

fun Page.getNumberFromProp(propName: String) =
    properties[propName]?.run {
        when (type) {
            PropertyType.Number -> number?.toInt()
            else -> null
        }
    }

fun Page.getDateFromProp(propName: String) = GregorianCalendar().apply {
    val date = getStringFromProp(propName)
    val simpleDateFormatter = SimpleDateFormat("yyyy-MM-dd")
    time = if (date != null) {
        simpleDateFormatter.parse(date) ?: Date(0)
    } else {
        Date(0)
    }
}

private fun Page.getIconUrl() = (icon as? File)?.file?.url