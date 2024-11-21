package office.effective.common.notifications

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.dsl.module
import java.io.ByteArrayInputStream

val firebaseDiModule  = module(createdAtStart = true) {
    val envString: String = System.getenv("FIREBASE_SA_JSON")
    single<FirebaseOptions> {
        FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(ByteArrayInputStream(envString.toByteArray())))
            .build()
    }
    single<FirebaseApp> {
        FirebaseApp.initializeApp(get<FirebaseOptions>())
    }
    single<FirebaseMessaging> {
        FirebaseMessaging.getInstance(get<FirebaseApp>())
    }
    single<INotificationSender> {
        FcmNotificationSender(get<FirebaseMessaging>())
    }
}
