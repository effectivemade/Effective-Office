package band.effective.office.elevator.ui.main.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.EffectiveTheme
import band.effective.office.elevator.ui.content.ContentComponent
import band.effective.office.elevator.components.TabNavigationItem
import band.effective.office.elevator.navigation.BookingTab
import band.effective.office.elevator.navigation.EmployeesTab
import band.effective.office.elevator.navigation.ProfileTab

@Composable
fun EffectiveBottomNavigation(
    activeComponent: ContentComponent.Child,
    onOutput: (ContentComponent.Output) -> Unit
) {
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
                onOutput(ContentComponent.Output.OpenBookingTab)
            }
            TabNavigationItem(
                tab = EmployeesTab,
                selected = activeComponent is ContentComponent.Child.Employee
            ) {
                onOutput(ContentComponent.Output.OpenEmployeeTab)
            }
            TabNavigationItem(
                tab = ProfileTab,
                selected = activeComponent is ContentComponent.Child.Profile
            ) {
                onOutput(ContentComponent.Output.OpenProfileTab)
            }
        }
    }
}
