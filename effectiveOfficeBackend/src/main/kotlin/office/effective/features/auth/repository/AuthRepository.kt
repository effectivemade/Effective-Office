package office.effective.features.auth.repository

import office.effective.common.exception.InstanceNotFoundException
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.find

/**
 * Provides methods to access to api_keys table in database
 * */
class AuthRepository(private val db: Database) {
    /**
     * @return encrypted api key [String]
     * @param keyEncrypted encrypted input [String]
     * @throws InstanceNotFoundException(ApiKeyEntity::class, "No such key found")
     * */
    fun findApiKey(keyEncrypted: String): String? {
        return db.apikeys.find { it.value.eq(keyEncrypted) }?.keyValue
    }
}