package band.effective.office.elevator.ui.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.EffectiveTheme
import band.effective.office.elevator.components.TabNavigationItem
import band.effective.office.elevator.navigation.BookingTab
import band.effective.office.elevator.navigation.EmployeesTab
import band.effective.office.elevator.navigation.ProfileTab
import band.effective.office.elevator.ui.booking.BookingScreen
import band.effective.office.elevator.ui.employee.Employee
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
        bottomBar = {
            Column {
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = EffectiveTheme.colors.divider.primary,
                    thickness = 1.dp
                )
                BottomNavigation(
                    backgroundColor = EffectiveTheme.colors.background.primary,
                ) {
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
        })
    { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Children(
                modifier = Modifier,
                stack = component.childStack,
                animation = tabAnimation()
            ) {
                when (val child = it.instance) {
                    is ContentComponent.Child.Profile -> Profile(child.component)
                    is ContentComponent.Child.Booking -> BookingScreen(child.component)
                    is ContentComponent.Child.Employee -> Employee(child.component)
                }
            }
        }
    }
}

private val ContentComponent.Child.index: Int
    get() =
        when (this) {
            is ContentComponent.Child.Booking -> 0
            is ContentComponent.Child.Employee -> 1
            is ContentComponent.Child.Profile -> 2
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
