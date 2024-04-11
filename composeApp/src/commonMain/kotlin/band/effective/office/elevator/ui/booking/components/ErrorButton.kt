package band.effective.office.elevator.ui.booking.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.MainRes
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ErrorButton(
    contentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick,
        shape = RectangleShape,
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.5.dp
        ),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(
                painterResource(MainRes.images.loading_ic),
                tint = contentColor,
                contentDescription = null
            )
            Text(
                text = stringResource(MainRes.strings.something_went_wrong),
                style = MaterialTheme.typography.subtitle1,
                color = contentColor
            )
        }
    }
}