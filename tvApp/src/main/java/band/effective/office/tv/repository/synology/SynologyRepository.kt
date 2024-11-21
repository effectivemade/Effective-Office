package band.effective.office.tv.repository.synology

import band.effective.office.tv.core.network.Either
import band.effective.office.tv.domain.model.synology.PhotoDomain
import band.effective.office.tv.network.synology.models.AuthModel
import kotlinx.coroutines.flow.Flow

interface SynologyRepository  {
    suspend fun auth(): Either<String, AuthModel>

    suspend fun getPhotosUrl(): Flow<Either<String, List<PhotoDomain>>>
}