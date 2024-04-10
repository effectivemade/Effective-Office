package band.effective.office.tablet.ui.mainScreen.slotComponent

import android.os.Build
import androidx.annotation.RequiresApi
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.ui.mainScreen.slotComponent.store.SlotStore
import band.effective.office.tablet.ui.mainScreen.slotComponent.store.SlotStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.component.KoinComponent

class SlotComponent(
    private val componentContext: ComponentContext,
    storeFactory: StoreFactory,
    val roomName: () -> String,
    openBookingDialog: (event: EventInfo, room: String) -> Unit
) : ComponentContext by componentContext, KoinComponent {
    @RequiresApi(Build.VERSION_CODES.N)
    private val store = SlotStoreFactory(storeFactory, roomName, openBookingDialog).create()

    @RequiresApi(Build.VERSION_CODES.N)
    @OptIn(ExperimentalCoroutinesApi::class)
    val state = store.stateFlow

    @RequiresApi(Build.VERSION_CODES.N)
    fun sendIntent(intent: SlotStore.Intent) {
        store.accept(intent)
    }
}