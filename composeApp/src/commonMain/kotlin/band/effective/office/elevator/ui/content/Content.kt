package band.effective.office.elevator.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.BottomNavigation
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.components.TabNavigationItem
import band.effective.office.elevator.navigation.BookingTab
import band.effective.office.elevator.navigation.EmployeesTab
import band.effective.office.elevator.navigation.MainTab
import band.effective.office.elevator.navigation.ProfileTab
import band.effective.office.elevator.ui.booking.BookingScreen
import band.effective.office.elevator.ui.employee.Employee
import band.effective.office.elevator.ui.main.MainScreen
import band.effective.office.elevator.ui.profile.Profile
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.Direction
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.StackAnimator
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.isEnter
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@Composable
fun Content(component: ContentComponent) {
    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance
    Scaffold(
        modifier = Modifier,
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                Children(
                    stack = component.childStack,
                    modifier = Modifier,
                    animation = tabAnimation()
                ) {
                    when (val child = it.instance) {
                        is ContentComponent.Child.Main -> MainScreen(child.component)
                        is ContentComponent.Child.Profile -> Profile(child.component)
                        is ContentComponent.Child.Booking -> BookingScreen(child.component)
                        is ContentComponent.Child.Employee -> Employee(child.component)
                    }
                }
            }
        },
        bottomBar = {
            BottomNavigation(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .fillMaxHeight(fraction = 0.107f),
                backgroundColor = Color.White
            ) {
                TabNavigationItem(
                    tab = MainTab,
                    selected = activeComponent is ContentComponent.Child.Main
                ) {
                    component.onOutput(ContentComponent.Output.OpenMainTab)
                }
                TabNavigationItem(
                    tab = BookingTab,
                    selected = activeComponent is ContentComponent.Child.Booking
                ) {
                    component.onOutput(ContentComponent.Output.OpenBookingTab)
                }
                TabNavigationItem(
                    tab = EmployeesTab,
                    selected = activeComponent is ContentComponent.Child.Employee
                ) {
                    component.onOutput(ContentComponent.Output.OpenEmployeeTab)
                }
                TabNavigationItem(
                    tab = ProfileTab,
                    selected = activeComponent is ContentComponent.Child.Profile
                ) {
                    component.onOutput(ContentComponent.Output.OpenProfileTab)
                }
            }
        }
    )
}

private val ContentComponent.Child.index: Int
    get() =
        when (this) {
            is ContentComponent.Child.Main -> 0
            is ContentComponent.Child.Booking -> 1
            is ContentComponent.Child.Employee -> 2
            is ContentComponent.Child.Profile -> 3
        }

@Composable
private fun tabAnimation(): StackAnimation<Any, ContentComponent.Child> =
    stackAnimation { child, otherChild, direction ->
        val index = child.instance.index
        val otherIndex = otherChild.instance.index
        val anim = slide()
        if ((index > otherIndex) == direction.isEnter) anim else anim.flipSide()
    }

private fun StackAnimator.flipSide(): StackAnimator =
    StackAnimator { direction, isInitial, onFinished, content ->
        invoke(
            direction = direction.flipSide(),
            isInitial = isInitial,
            onFinished = onFinished,
            content = content,
        )
    }

@Suppress("OPT_IN_USAGE")
private fun Direction.flipSide(): Direction =
    when (this) {
        Direction.ENTER_FRONT -> Direction.ENTER_BACK
        Direction.EXIT_FRONT -> Direction.EXIT_BACK
        Direction.ENTER_BACK -> Direction.ENTER_FRONT
        Direction.EXIT_BACK -> Direction.EXIT_FRONT
    }

