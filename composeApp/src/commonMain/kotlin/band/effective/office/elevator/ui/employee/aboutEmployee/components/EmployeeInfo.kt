package band.effective.office.elevator.ui.employee.aboutEmployee.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.ExtendedThemeColors
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmployeeInfo(
    modifier: Modifier = Modifier,
    icon: ImageResource,
    iconTitle: String,
    value: String?,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.LightGray.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp)
            .combinedClickable(
                onLongClick = onLongClick,
                onClick = onClick,
            ),

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = Color.LightGray.copy(alpha = 0.5f)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(4.dp),
                    tint = ExtendedThemeColors.colors.trinidad_400,
                )

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = iconTitle,
                    style = TextStyle(color = Color.Gray, fontSize = 16.sp),
                )
                value?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.caption,
                        fontSize = 16.sp,
                        color = Color.Gray,
                    )
                }
            }
        }
    }
}
