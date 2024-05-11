package band.effective.feedback.data.ydb

import band.effective.feedback.domain.model.FeedbackStorage
import band.effective.feedback.domain.model.FeedbackStorageType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object FeedbackStorageDescriptor : KSerializer<FeedbackStorage> {
    override val descriptor: SerialDescriptor = FeedbackStorageJson.serializer().descriptor

    override fun deserialize(decoder: Decoder): FeedbackStorage {
        val json = decoder.decodeSerializableValue(FeedbackStorageJson.serializer())
        return when (json.type) {
            FeedbackStorageType.YDB -> FeedbackStorage.Ydb
            FeedbackStorageType.MATTERMOST -> FeedbackStorage.Mattermost
            FeedbackStorageType.NOTION -> FeedbackStorage.Notion(json.notionDb!!)
            FeedbackStorageType.UNSUPPORTED -> FeedbackStorage.Mattermost
        }
    }

    override fun serialize(encoder: Encoder, value: FeedbackStorage) {
        val type = value.type
        val notionDb = if (value is FeedbackStorage.Notion) value.dbToken else null
        val json = FeedbackStorageJson(type, notionDb)
        encoder.encodeSerializableValue(FeedbackStorageJson.serializer(), json)
    }
}

@Serializable
private data class FeedbackStorageJson(val type: FeedbackStorageType, val notionDb: String? = null)