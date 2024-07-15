package band.effective.office.tablet.ui.uiComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.core.MainRes
import band.effective.office.tablet.ui.loader.Loader
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.h6_button

@Composable
fun FreeBookingButtonView(
    modifier: Modifier,
    shape: RoundedCornerShape,
    text: String,
    onClick: () -> Unit,
    isLoading: Boolean
) {
    Button(
        modifier = modifier,
        elevation = ButtonDefaults.elevation(0.dp),
        colors = ButtonDefaults.buttonColors(Color.White),
        shape = shape,
        onClick = {
            onClick()
        }
    ) {
        Box(contentAlignment = Alignment.Center)
        {
            if (isLoading)
                Loader(elementColor = MaterialTheme.colors.onSecondary)
            else
                Row{
                    Text(
                        text = text,
                        style = MaterialTheme.typography.h6_button,
                        color = LocalCustomColorsPalette.current.busyStatus
                    )
                    Spacer(modifier = Modifier.size(15.dp))
                    Icon(
                        imageVector = ImageVector.vectorResource(MainRes.image.cross),
                        contentDescription = "Cross",
                        modifier = Modifier.size(20.dp),
                        tint = LocalCustomColorsPalette.current.busyStatus
                    )
                }

        }
    }
}