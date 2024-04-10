package band.effective.office.tablet.ui.bookingComponents.pickerDateTime

import android.os.Build
import androidx.annotation.RequiresApi
import band.effective.office.tablet.ui.modal.ModalWindow
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
class DateTimePickerComponent(
    private val componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val onSelectDate: (Calendar) -> Unit,
    private val onCloseRequest: () -> Unit,
    val initDate: () -> Calendar
) : ComponentContext by componentContext, ModalWindow {

    private val dateTimePickerStore = instanceKeeper.getStore {
        DateTimePickerStoreFactory(storeFactory, onCloseRequest, onSelectDate,initDate()).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = dateTimePickerStore.stateFlow

    fun sendIntent(intent: DateTimePickerStore.Intent) {
        dateTimePickerStore.accept(intent)
    }
}