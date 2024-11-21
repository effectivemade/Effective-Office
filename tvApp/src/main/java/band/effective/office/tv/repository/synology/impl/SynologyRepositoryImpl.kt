package band.effective.office.tv.repository.synology.impl

import band.effective.office.tv.core.network.Either
import band.effective.office.tv.domain.model.synology.PhotoDomain
import band.effective.office.tv.domain.use_cases.AuthSynologyUseCase
import band.effective.office.tv.domain.use_cases.PhotoSynologyUseCase
import band.effective.office.tv.network.synology.models.AuthModel
import band.effective.office.tv.repository.synology.SynologyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SynologyRepositoryImpl @Inject constructor(
    private val loginSynology: AuthSynologyUseCase,
    private val photoSynology: PhotoSynologyUseCase
) : SynologyRepository {

    private var sid: String? = null

    override suspend fun auth(): Either<String, AuthModel> =
        withContext(Dispatchers.IO) {
            when (val authModel = loginSynology()) {
                is Either.Success -> {
                    sid = authModel.data.sid
                    authModel
                }
                is Either.Failure -> authModel
            }
        }

    override suspend fun getPhotosUrl(): Flow<Either<String, List<PhotoDomain>>> =
        withContext(Dispatchers.IO) {
            photoSynology(
                sid = sid?:"",
            )
        }
}
