package band.effective.office.elevator.ui.authorization.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedThemeColors

@Composable
fun AuthTabItem(selected: Boolean, index: Int) {

    val selectedColor = remember { mutableStateOf(ExtendedThemeColors.colors.purple_heart_700) }
    val unselectedColor = remember { mutableStateOf(ExtendedThemeColors.colors._66x) }

    Tab(
        selectedContentColor = selectedColor.value,
        unselectedContentColor = unselectedColor.value,
        selected = selected,
        onClick = { },
        enabled = false,
        modifier = Modifier
            .height(4.dp),
        icon = {
            Box(
                modifier = Modifier.padding(
                    when (index) {
                        0 -> PaddingValues(start = 6.dp)
                        1 -> PaddingValues(horizontal = 6.dp)
                        else -> PaddingValues(end = 6.dp)
                    }
                ).clip(RoundedCornerShape(8.dp))
            ) {
                Divider(
                    modifier = Modifier.height(4.dp),
                    color = if (selected) selectedColor.value else unselectedColor.value
                )
            }
        }
    )
}