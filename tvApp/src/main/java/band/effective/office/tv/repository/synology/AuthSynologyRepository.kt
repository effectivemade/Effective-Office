package band.effective.office.tv.repository.synology

import band.effective.office.tv.core.network.Either
import band.effective.office.tv.network.synology.models.AuthModel

interface AuthSynologyRepository {
    suspend fun auth(): Either<String, AuthModel>
}