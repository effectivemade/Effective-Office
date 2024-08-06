package band.effective.office.tv.domain.use_cases

import band.effective.office.tv.core.network.Either
import band.effective.office.tv.network.synology.models.AuthModel
import band.effective.office.tv.repository.synology.AuthSynologyRepository
import javax.inject.Inject

class AuthSynologyUseCase @Inject constructor(
    private val authSynologyRepository: AuthSynologyRepository
) {
    suspend operator fun invoke(): Either<String, AuthModel> =
        authSynologyRepository.auth()
}