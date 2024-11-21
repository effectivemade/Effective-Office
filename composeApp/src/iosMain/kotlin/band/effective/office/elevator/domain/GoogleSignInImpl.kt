package band.effective.office.elevator.domain

import band.effective.office.elevator.MainRes
import band.effective.office.elevator.data.ApiResponse
import band.effective.office.elevator.domain.models.GoogleAccount
import band.effective.office.elevator.ui.uiViewController
import cocoapods.GoogleSignIn.GIDConfiguration
import cocoapods.GoogleSignIn.GIDGoogleUser
import cocoapods.GoogleSignIn.GIDSignIn
import dev.icerock.moko.resources.desc.desc
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import platform.UIKit.UIScreen
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.round

@OptIn(ExperimentalForeignApi::class)
class GoogleSignInImpl(gidClientId: String, serverClientId: String) : GoogleSignIn {

    private val scope = CoroutineScope(Dispatchers.Main)

    init {
        GIDSignIn.sharedInstance.setConfiguration(
            configuration = GIDConfiguration(
                clientID = gidClientId, serverClientID = serverClientId
            )
        )
    }

    override suspend fun retrieveAuthorizedUser(): ApiResponse<GoogleAccount, Error> {
        return suspendCoroutine { continuation ->
            GIDSignIn.sharedInstance.restorePreviousSignInWithCompletion { result, _ ->
                scope.launch {
                    result?.profile?.let {
                        continuation.resume(
                            ApiResponse.Success(
                                result.toGoogleAccount()
                            )
                        )
                    } ?: continuation.resume(ApiResponse.Error.UnknownError)
                }
            }
        }
    }

    override fun signIn(callback: SignInResultCallback) {
        GIDSignIn.sharedInstance.signInWithPresentingViewController(uiViewController) { result, error ->
            if (result == null) callback.onFailure(MainRes.strings.something_went_wrong.desc().localized())
            else {
                val account = result.user
                account.profile?.let {
                    callback.onSuccess(account.toGoogleAccount())
                } ?: callback.onFailure("")
            }
        }
    }

    override fun signOut() {
        GIDSignIn.sharedInstance.signOut()
    }

    private fun GIDGoogleUser.toGoogleAccount(): GoogleAccount{
        val dimension = round(100 * UIScreen.mainScreen.scale)
        return GoogleAccount(
            email = profile!!.email,
            name = profile!!.name,
            photoUrl = profile!!.imageURLWithDimension(dimension = dimension.toULong())?.absoluteString,
            idToken = idToken?.tokenString
        )
    }
}
