package band.effective.office.tablet.ui.bookingComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.core.MainRes
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.h8

@Composable
fun EventDurationView(
    modifier: Modifier = Modifier,
    currentDuration: Int,
    increment: () -> Unit,
    decrement: () -> Unit
) {
    val space = 50.dp
    Column(modifier = modifier) {
        Text(
            text = MainRes.string.select_length_title,
            color = LocalCustomColorsPalette.current.secondaryTextAndIcon,
            style = MaterialTheme.typography.h8
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                modifier = Modifier.fillMaxHeight().weight(1f).clip(RoundedCornerShape(15.dp)),
                onClick = { decrement() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LocalCustomColorsPalette.current.elevationBackground
                )
            ) {
                Text(
                    text = MainRes.string.minus_date_button_string,
                    style = MaterialTheme.typography.h6
                )
            }
            Text(
                modifier = Modifier.weight(1f),
                text = currentDuration.getDurationString(),
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center
            )
            Button(
                modifier = Modifier.fillMaxHeight().weight(1f).clip(RoundedCornerShape(15.dp)),
                onClick = {
                    increment()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LocalCustomColorsPalette.current.elevationBackground
                )
            ) {
                Text(
                    text = MainRes.string.plus_date_button_string,
                    style = MaterialTheme.typography.h6
                )
            }
        }
    }

}

private fun Int.getDurationString(): String {
    val hours = this / 60
    val minutes = this % 60
    return when {
        hours == 0 -> "$minutes${MainRes.string.short_minuets}"
        minutes == 0 -> "$hours${MainRes.string.short_hours}"
        else -> "$hours${MainRes.string.short_hours} $minutes${MainRes.string.short_minuets}"
    }
}
