package band.effective.office.tv.repository.synology.impl

import band.effective.office.tv.BuildConfig
import band.effective.office.tv.core.network.Either
import band.effective.office.tv.core.network.ErrorReason
import band.effective.office.tv.core.network.unpack
import band.effective.office.tv.network.synology.SynologyApi
import band.effective.office.tv.network.synology.models.AuthModel
import band.effective.office.tv.network.synology.models.response.SynologyAuthResponse
import band.effective.office.tv.repository.synology.AuthSynologyRepository
import javax.inject.Inject

class AuthSynologyRepositoryImpl @Inject constructor(
    private val synologyApi: SynologyApi
) : AuthSynologyRepository {

    override suspend fun auth(): Either<String, AuthModel> {
        val res: Either<ErrorReason, SynologyAuthResponse> =
            synologyApi.
            auth(
                version = 3,
                method = "login",
                login = BuildConfig.synologyLogin,
                password = BuildConfig.synologyPassword,
            )

        return res.unpack(
            success = { response ->
                val sid = response.data?.sid ?: ""
                Either.Success(AuthModel(sid))
            },
            error = { error ->
                Either.Failure(error.message)
            }
        )
    }
}