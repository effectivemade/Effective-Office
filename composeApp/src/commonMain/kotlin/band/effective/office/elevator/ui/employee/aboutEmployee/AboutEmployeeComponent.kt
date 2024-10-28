package band.effective.office.elevator.ui.employee.aboutEmployee

import band.effective.office.elevator.domain.models.EmployeeInfo
import band.effective.office.elevator.ui.employee.aboutEmployee.store.AboutEmployeeStore
import band.effective.office.elevator.ui.employee.aboutEmployee.store.AboutEmployeeStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


class AboutEmployeeComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit,
    employee: EmployeeInfo
) :
    ComponentContext by componentContext {

    private val aboutEmployeeStore = instanceKeeper.getStore {
        AboutEmployeeStoreFactory(
            storeFactory = storeFactory,
            employeeInfo = employee
        ).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<AboutEmployeeStore.State> = aboutEmployeeStore.stateFlow
    val label: Flow<AboutEmployeeStore.Label> = aboutEmployeeStore.labels

    fun onEvent(event: AboutEmployeeStore.Intent) {
        aboutEmployeeStore.accept(event)
    }
    fun onOutput(output: Output){
        output(output)
    }
    sealed interface Output {
        object OpenAllEmployee : Output
    }
}
