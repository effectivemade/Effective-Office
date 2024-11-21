package band.effective.office.elevator.ui.main.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.EffectiveButton
import band.effective.office.elevator.components.OutlinedPrimaryButton
import band.effective.office.elevator.textInBorderGray
import band.effective.office.elevator.ui.employee.aboutEmployee.models.BookingsFilter
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun BottomDialog(modifier: Modifier, title: String,  onClickCloseBottomDialog:(BookingsFilter) -> Unit) {

    var isExpanded by remember { mutableStateOf(true) }
    var isExpandedScBtn by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .background(ExtendedThemeColors.colors.whiteColor)
            .padding(vertical = 24.dp, horizontal = 16.dp)
            .fillMaxWidth()
            .then(other = modifier)
    ) {
        Text(text = title, style = MaterialTheme.typography.h6, color = Color.Black)
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedIconButton(
                onClick = { if(isExpandedScBtn) isExpanded = !isExpanded },
                border = BorderStroke(1.dp, if(isExpanded) ExtendedThemeColors.colors.purple_heart_800 else textInBorderGray),
                modifier = Modifier.weight(.1f),
                shape = RoundedCornerShape(12.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Done,
                        contentDescription = "done button",
                        tint = ExtendedThemeColors.colors.purple_heart_800,
                        modifier = Modifier.size(if(isExpanded) 24.dp else 0.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(MainRes.strings.meeting_room),
                        color = if(isExpanded) ExtendedThemeColors.colors.purple_heart_800 else textInBorderGray,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedIconButton(
                onClick = {
                    if(isExpanded) isExpandedScBtn =!isExpandedScBtn},
                border = BorderStroke(1.dp, if(isExpandedScBtn) ExtendedThemeColors.colors.purple_heart_800 else textInBorderGray),
                modifier = Modifier.weight(.1f),
                shape = RoundedCornerShape(12.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Done,
                        contentDescription = "done button",
                        tint = ExtendedThemeColors.colors.purple_heart_800,
                        modifier = Modifier.size(if (isExpandedScBtn)24.dp else 0.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(MainRes.strings.workplace),
                        color = if(isExpandedScBtn) ExtendedThemeColors.colors.purple_heart_800 else textInBorderGray,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            OutlinedPrimaryButton(
                onClick = {
                    isExpanded = true
                    isExpandedScBtn = true
                },
                title = MainRes.strings.reset_filter,
                modifier = Modifier.weight(.1f),
                roundedCorner = 8.dp,
                padding = 12.dp
            )
            Spacer(modifier = Modifier.width(16.dp))
            EffectiveButton(
                buttonText = stringResource(MainRes.strings.ok),
                onClick = { onClickCloseBottomDialog(BookingsFilter(isExpanded, isExpandedScBtn)) },
                modifier = Modifier.weight(.1f),
                roundedCorner = 8.dp,
                contentPadding = 12.dp
            )
        }
    }
}
