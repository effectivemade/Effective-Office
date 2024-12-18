package band.effective.office.shareddesk.interactivemap.presentation.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddLocationAlt
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.WrongLocation
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ControlPanel(
    modifier: Modifier,
    onSaveClick: () -> Unit,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit,
    onRefreshClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
    ) {
        ControlPanelButton(
            modifier = Modifier,
            onClick = onAddClick,
            text = "Add Table",
            icon = Icons.Outlined.AddLocationAlt,
        )

        ControlPanelButton(
            modifier = Modifier,
            onClick = onRemoveClick,
            text = "Remove Table",
            icon = Icons.Outlined.WrongLocation,
        )

        ControlPanelButton(
            modifier = Modifier,
            onClick = onSaveClick,
            text = "Save changes",
            icon = Icons.Outlined.Save,
        )

        ControlPanelButton(
            modifier = Modifier,
            onClick = onRefreshClick,
            text = "Refresh",
            icon = Icons.Outlined.Refresh,
        )
    }
}

@Composable
fun ControlPanelButton(
    modifier: Modifier,
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        content = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text)
                Spacer(Modifier.width(4.dp))
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = icon,
                    contentDescription = text,
                )
            }
        }
    )
}