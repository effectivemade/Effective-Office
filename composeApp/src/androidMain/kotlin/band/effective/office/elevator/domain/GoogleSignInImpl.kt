package band.effective.office.elevator.domain

import band.effective.office.elevator.AndroidApp
import band.effective.office.elevator.AppActivity
import band.effective.office.elevator.data.ApiResponse
import band.effective.office.elevator.domain.models.GoogleAccount
import band.effective.office.elevator.utils.LastOpenActivityProvider
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.google.android.gms.auth.api.signin.GoogleSignIn as GoogleAuthorization


class GoogleSignInImpl : GoogleSignIn {

    private val activity = LastOpenActivityProvider.lastOpenedActivity()!! as AppActivity

    override suspend fun retrieveAuthorizedUser(): ApiResponse<GoogleAccount, Error> {
        return suspendCoroutine { continuation ->
            activity.appActivityLifecycleObserver.retrieveAuthorizedUser(
                object: SignInResultCallback {
                    override fun onSuccess(googleAccount: GoogleAccount) {
                        continuation.resume(ApiResponse.Success(googleAccount))
                    }

                    override fun onFailure(message: String) {
                        continuation.resume(ApiResponse.Error.UnknownError)
                    }

                }
            )
        }
    }


    override fun signIn(callback: SignInResultCallback) {
        activity.appActivityLifecycleObserver.signIn(callback)
    }

    override fun signOut() {
        activity.appActivityLifecycleObserver.signOut()
    }

}
