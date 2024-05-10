package band.effective.feedback.data.notion

import notion.api.v1.NotionClient
import notion.api.v1.model.pages.PageParent
import notion.api.v1.model.pages.PageProperty

class NotionDb(private val notionClient: NotionClient, private val notionDatabaseId: String) {

    fun addFeedback(){
        notionClient.createPage(
            parent = PageParent.database(notionDatabaseId),
            properties = mapOf(
                "Name" to PageProperty(title = "Test".asRichText()),
                "Message" to PageProperty(richText = "Test Message".asRichText())
            )
        )
    }

    private fun String.asRichText(): List<PageProperty.RichText> =
        listOf(PageProperty.RichText(text = PageProperty.RichText.Text(content = this)))

}