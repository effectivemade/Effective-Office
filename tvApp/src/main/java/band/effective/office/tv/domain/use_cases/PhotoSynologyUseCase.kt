package band.effective.office.tv.domain.use_cases

import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.core.network.entity.map
import band.effective.office.tv.domain.model.synology.PhotoDomain
import band.effective.office.tv.repository.synology.PhotoSynologyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PhotoSynologyUseCase @Inject constructor(
    private val photoSynologyRepository: PhotoSynologyRepository
) {
    suspend operator fun invoke(sid: String): Flow<Either<String, List<PhotoDomain>>> {
        val eitherFlow: Flow<Either<String, List<PhotoDomain>>> =
            photoSynologyRepository.getPhotosUrl(sid = sid)

        eitherFlow.map { either ->
            either.map { photos-> photos.shuffled()}
        }

        return eitherFlow
    }
}