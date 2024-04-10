package band.effective.office.elevator.ui.booking.components.modals

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.textInBorderGray
import band.effective.office.elevator.ui.booking.components.HorizontalGirdItems
import band.effective.office.elevator.ui.booking.models.WorkspaceZoneUI
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ChooseZone(
    sheetTile: String,
    workSpacecZone: List<WorkspaceZoneUI>,
    onClickCloseChoseZone: () -> Unit,
    onClickConfirmSelectedZone: () -> Unit,
    onClickZone: (WorkspaceZoneUI) -> Unit
) {
    val countItemsInRow = 2
    val selectedZones: MutableList<WorkspaceZoneUI> = mutableListOf()
    selectedZones.addAll(workSpacecZone)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        Divider(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(fraction = .3f)
                .height(4.dp)
                .background(
                    color = ExtendedThemeColors.colors.dividerColor,
                    shape = RoundedCornerShape(size = 16.dp)
                )
                .padding(
                    bottom = 8.dp
                )
        )
        Row(modifier = Modifier.padding(top = 10.dp, start = 16.dp, end = 16.dp)) {
            IconButton(
                onClick = onClickCloseChoseZone,
                modifier = Modifier
                    .align(Alignment.Top)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Krestik",
                    tint = Color.Black
                )
            }
            Text(
                text = sheetTile,
                style = MaterialTheme.typography.subtitle1,
                fontSize = 20.sp,
                fontWeight = FontWeight(600),
                color = Color.Black,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth(fraction = 1.0f)
                .height(height = 1.dp)
                .background(
                    color = ExtendedThemeColors.colors._66x
                )
        )

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalGirdItems(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            countItemsInRow = countItemsInRow,
            listItems = workSpacecZone,
            horizontalPaddingContent = 12.dp,
            verticalPaddingContent = 12.dp
        ) { workSpaceZone, _, _ ->
            WorkingZones(
                workspaceZoneUI = workSpaceZone,
                onClickZone = { onClickZone(it) }
            )
        }

        Button(
            onClick = { onClickConfirmSelectedZone() },
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 15.dp, vertical = 10.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ExtendedThemeColors.colors.trinidad_600,
            )
        ) {
            Text(
                text = stringResource(MainRes.strings.confirm_booking),
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Composable
fun WorkingZones(
    modifier: Modifier = Modifier,
    workspaceZoneUI: WorkspaceZoneUI,
    onClickZone: (WorkspaceZoneUI) -> Unit,
) {
    Button(
        onClick = { onClickZone(workspaceZoneUI) },
        colors = ButtonDefaults.buttonColors(ExtendedThemeColors.colors.whiteColor),
        modifier = modifier.padding(end = 10.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (workspaceZoneUI.isSelected) ExtendedThemeColors.colors.purple_heart_800 else textInBorderGray
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = ButtonDefaults.elevation(0.dp, 2.dp, 0.dp)
    ) {
        if (workspaceZoneUI.isSelected) {
            Icon(
                imageVector = Icons.Rounded.Done,
                tint = ExtendedThemeColors.colors.purple_heart_800,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterVertically),
                contentDescription = "galochka"
            )
        }
        Text(
            text = workspaceZoneUI.name,
            fontSize = 16.sp,
            fontWeight = FontWeight(500),
            maxLines = 1,
            color = if (workspaceZoneUI.isSelected) ExtendedThemeColors.colors.purple_heart_800
            else textInBorderGray
        )
    }
}