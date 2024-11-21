package band.effective.office.elevator.domain

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import band.effective.office.elevator.AppActivity
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.OfficeElevatorConfig
import band.effective.office.elevator.domain.models.GoogleAccount
import band.effective.office.elevator.utils.getStringResource
import com.google.android.gms.auth.api.proxy.AuthApiStatusCodes
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes.SIGN_IN_CANCELLED
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import io.github.aakira.napier.Napier


class AppActivityLifecycleObserver(
    activity: AppActivity,
    private val registry: ActivityResultRegistry
) : DefaultLifecycleObserver {

    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestIdToken(OfficeElevatorConfig.webClient)
        .build()

    private val signInClient = GoogleSignIn.getClient(activity, gso)
    private val context = activity.applicationContext

    private lateinit var launcher: ActivityResultLauncher<Intent>

    private lateinit var callback: SignInResultCallback

    override fun onCreate(owner: LifecycleOwner) {
        launcher = registry.register(
            "key",
            owner,
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task, callback)
        }
    }

    private fun handleSignInResult(
        task: Task<GoogleSignInAccount>,
        callback: SignInResultCallback
    ) {
        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
            Napier.d { "ID_TOKEN = ${account.idToken}" }

            callback.onSuccess(account.toGoogleAccount())
        } catch (e: ApiException) {
            Napier.e(
                "signInResult:failed code=" + e.statusCode + " | description: ${
                    GoogleSignInStatusCodes.getStatusCodeString(e.statusCode)
                }"
            )
            val errorMessage = when (e.statusCode) {
                AuthApiStatusCodes.NETWORK_ERROR -> getStringResource(MainRes.strings.network_error, context)
                AuthApiStatusCodes.DEVELOPER_ERROR -> getStringResource(MainRes.strings.developer_error, context)
                AuthApiStatusCodes.CANCELED -> getStringResource(MainRes.strings.cancelled_error, context)
                AuthApiStatusCodes.INVALID_ACCOUNT -> getStringResource(MainRes.strings.invalid_account_error, context)
                AuthApiStatusCodes.TIMEOUT -> getStringResource(MainRes.strings.timout_error, context)
                SIGN_IN_CANCELLED -> getStringResource(MainRes.strings.you_need_to_sign_in, context)
                else -> getStringResource(MainRes.strings.something_went_wrong, context)
            }
            callback.onFailure(errorMessage)
        }
    }

    fun signIn(callback: SignInResultCallback) {
        this.callback = callback
        launcher.launch(signInClient.signInIntent)
    }
    fun signOut() {
        signInClient.signOut()
    }

    fun retrieveAuthorizedUser(callback: SignInResultCallback) {
        val task = signInClient.silentSignIn()
        if (task.isSuccessful) {
            // There's immediate result available.
            val signInAccount = task.result
            callback.onSuccess(signInAccount.toGoogleAccount())
        } else {
            task.addOnCompleteListener { task ->
                try {
                    val signInAccount = task.getResult(ApiException::class.java)
                    callback.onSuccess(signInAccount.toGoogleAccount())
                } catch (apiException: ApiException) {
                    callback.onFailure(apiException.message.orEmpty())
                }
            }
        }
    }

    private fun GoogleSignInAccount.toGoogleAccount() =
        GoogleAccount(
            email = email!!,
            name = displayName!!,
            photoUrl = photoUrl.toString(),
            idToken = idToken
        )
}
