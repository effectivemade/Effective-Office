package band.effective.office.elevator.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.EffectiveTheme
import band.effective.office.elevator.navigation.Tab
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun RowScope.TabNavigationItem(
    tab: Tab,
    selected: Boolean,
    onSelect: () -> Unit,
) {
    val selectedColor = EffectiveTheme.colors.icon.accent
    val unselectedColor = EffectiveTheme.colors.icon.primary
    BottomNavigationItem(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        selected = selected,
        selectedContentColor = selectedColor,
        unselectedContentColor = unselectedColor,
        onClick = onSelect,
        icon = {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(size = 24.dp),
                    painter = painterResource(tab.icon),
                    contentDescription = stringResource(tab.title)
                )
                Spacer(modifier = Modifier.height(height = 4.dp))
                Text(
                    text = stringResource(tab.title),
                    style = EffectiveTheme.typography.xsRegular.copy(
                        color = if (selected) selectedColor else unselectedColor
                    ),
                    maxLines = 1
                )
            }
        }
    )
}
