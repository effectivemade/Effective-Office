package band.effective.office.tablet

import android.app.Application
import band.effective.office.tablet.di.initKoin
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(this)
        subscribeOnFirebaseTopics()
    }

    private fun subscribeOnFirebaseTopics() {
        val topicNameList: List<String> = get(qualifier = named("FireBaseTopics"))
        topicNameList.forEach { topic ->
            FirebaseMessaging.getInstance().subscribeToTopic(topic)
        }
    }
}