package band.effective.office.elevator.ui.booking.components.modals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.components.Elevation
import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.radioButtonColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BookingRepeatElement(
    selected: Boolean,
    bookingText: String,
    bookingPeriod: BookingPeriod,
    onSelect: (String) -> Unit,
    onSelected: (Pair<String, BookingPeriod>) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = ExtendedThemeColors.colors.transparentColor
        ),
        elevation = Elevation(),
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        onClick = {
            coroutineScope.launch {
                onSelect(bookingText)
                delay(100)
                onSelected((bookingText to bookingPeriod))
            }
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RadioButton(
                enabled = false,
                selected = selected,
                colors = RadioButtonDefaults.colors(
                    disabledSelectedColor = radioButtonColor,
                ),
                onClick = null
            )
            Text(
                text = bookingText,
                style = MaterialTheme.typography.button.copy(
                    color = ExtendedThemeColors.colors.radioTextColor,
                    fontWeight = FontWeight(400),
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier.fillMaxWidth().wrapContentHeight()
            )
        }
    }
}