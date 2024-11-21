package band.effective.office.elevator.ui.employee

import band.effective.office.elevator.domain.models.EmployeeInfo
import band.effective.office.elevator.ui.employee.aboutEmployee.AboutEmployeeComponent
import band.effective.office.elevator.ui.employee.allEmployee.EmployeeComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory

class FullEmployeeComponent (
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory) :
    ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val stack = childStack(
        source = navigation,
        initialStack = { listOf(Config.AllEmployee) },
        childFactory = ::child,
        handleBackButton = true
    )

    val childStack: Value<ChildStack<*, Child>> = stack

    private fun child(config: Config, componentContext: ComponentContext):Child =
        when(config){
            is Config.AllEmployee -> Child.AllEmployeeChild(
                EmployeeComponent(
                    componentContext,
                    storeFactory,
                    ::employeeOutput
                )
            )

            is Config.AboutEmployee -> Child.AboutEmployeeChild(
                AboutEmployeeComponent(
                    componentContext,
                    storeFactory,
                    ::employeeOutput,
                    config.employee
                )
            )
        }

    private fun employeeOutput(output: EmployeeComponent.Output) {
        when(output){
            is EmployeeComponent.Output.OpenProfileScreen -> navigation.bringToFront(Config.AboutEmployee(output.employee))
            is EmployeeComponent.Output.OpenNewListOfEmployees -> TODO()//unused
        }
    }

    private fun employeeOutput(output: AboutEmployeeComponent.Output) {
        when(output){
            is AboutEmployeeComponent.Output.OpenAllEmployee -> navigation.replaceAll(Config.AllEmployee)
        }
    }

    sealed class Child{
        class AllEmployeeChild(val component: EmployeeComponent) : Child()
        class AboutEmployeeChild(val component: AboutEmployeeComponent): Child()
    }

    sealed class Config: Parcelable {
        @Parcelize
        object AllEmployee: Config()

        @Parcelize
        data class AboutEmployee(val employee: EmployeeInfo): Config()
    }
}