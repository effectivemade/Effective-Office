package band.effective.office.elevator.ui.booking.components.modals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.Elevation
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun BookingRepeatContextMenu (onClick: (String) -> Unit) {
    val dropDownList =
        listOf(
            MainRes.strings.week,
            MainRes.strings.month,
            MainRes.strings.year
        )

    Column(
        modifier = Modifier
                .padding(end = 24.dp, bottom = 24.dp)
                .shadow( elevation = 10.dp, shape = RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(color = Color.White)
                .wrapContentSize()
    ) {
        for (item in dropDownList) {
            with(stringResource(item)) {
                Column(modifier = Modifier.wrapContentSize()) {
                    Button(
                        onClick = { onClick(this@with) },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent,
                        ),
                        elevation = Elevation(),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = this@with,
                            color = MaterialTheme.colors.secondary,
                            modifier = Modifier.wrapContentSize())
                    }
                    if (dropDownList.indexOf(item) !=dropDownList.lastIndex)
                        Divider(
                            modifier = Modifier.height(1.dp)
                                .background(color = ExtendedThemeColors.colors._66x)
                        )
                }
            }
        }
    }
}