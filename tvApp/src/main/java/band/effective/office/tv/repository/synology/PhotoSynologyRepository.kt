package band.effective.office.tv.repository.synology

import band.effective.office.tv.core.network.Either
import band.effective.office.tv.domain.model.synology.PhotoDomain
import kotlinx.coroutines.flow.Flow

interface PhotoSynologyRepository {
    suspend fun getPhotosUrl(sid: String):
            Flow<Either<String, List<PhotoDomain>>>
}

