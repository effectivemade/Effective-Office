package band.effective.office.tv.domain.use_cases

import band.effective.office.tv.core.network.Either
import band.effective.office.tv.core.network.map
import band.effective.office.tv.domain.model.synology.PhotoDomain
import band.effective.office.tv.repository.synology.PhotoSynologyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PhotoSynologyUseCase @Inject constructor(
    private val photoSynologyRepository: PhotoSynologyRepository
) {
    suspend operator fun invoke(sid: String): Flow<Either<String, List<PhotoDomain>>> =
        photoSynologyRepository.getPhotosUrl(sid = sid)
            .map { either ->
                when (either) {
                    is Either.Failure -> either
                    is Either.Success -> either.map { photos ->
                        photos.shuffled()
                    }
                }
            }
}