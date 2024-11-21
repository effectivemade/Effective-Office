package band.effective.office.tvServer.utils

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import java.io.ByteArrayInputStream

fun firebase(): FirebaseMessaging {
    val cred = System.getenv()["FCM_CRED"] ?: ""
    val stream = ByteArrayInputStream(cred.toByteArray())
    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(stream))
        .build()
    return FirebaseMessaging.getInstance(FirebaseApp.initializeApp(options))
}