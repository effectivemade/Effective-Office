package office.effective.features.user.converters

import office.effective.features.user.repository.IntegrationEntity
import office.effective.model.IntegrationModel

/**
 * Converters between [IntegrationModel] and [IntegrationEntity]
 * */
class IntegrationModelEntityConverter {
    /**
     * Converts [IntegrationModel] to [IntegrationEntity]
     * @param integrationModel [IntegrationModel] input to convert to [IntegrationEntity]
     * @return resulting [IntegrationEntity] object
     * */
    fun modelToEntity(integrationModel: IntegrationModel): IntegrationEntity {
        return IntegrationEntity {
            id = integrationModel.id
            name = integrationModel.name;
            iconUrl = integrationModel.iconUrl
        }
    }

    /**
     * Converts [IntegrationEntity] to [IntegrationModel]
     * @param integrationEntity [IntegrationEntity] input to convert to [IntegrationModel]
     * @param valueStr [String] users_integrations value
     * @return resulting [IntegrationModel] object
     * */
    fun entityToModel(integrationEntity: IntegrationEntity, valueStr: String): IntegrationModel {
        return IntegrationModel(
            id = integrationEntity.id!!,
            iconUrl = integrationEntity.iconUrl,
            name = integrationEntity.name,
            valueStr = valueStr
        )
    }
}
