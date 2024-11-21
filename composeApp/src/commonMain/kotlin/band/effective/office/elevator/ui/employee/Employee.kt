package band.effective.office.elevator.ui.employee

import androidx.compose.runtime.Composable
import band.effective.office.elevator.ui.employee.aboutEmployee.AboutEmployee
import band.effective.office.elevator.ui.employee.allEmployee.EmployeeScreen
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation

@Composable
fun Employee (component: FullEmployeeComponent){
    Children(
        stack = component.childStack,
        animation = stackAnimation(fade() + scale()),
    ){
        when(val child = it.instance){
            is FullEmployeeComponent.Child.AllEmployeeChild -> EmployeeScreen(child.component)
            is FullEmployeeComponent.Child.AboutEmployeeChild -> AboutEmployee(child.component)
        }
    }
}