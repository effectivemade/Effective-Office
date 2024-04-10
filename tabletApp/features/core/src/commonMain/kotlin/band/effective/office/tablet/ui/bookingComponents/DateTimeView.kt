package band.effective.office.tablet.ui.bookingComponents

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.core.MainRes
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.h8
import band.effective.office.tablet.utils.CalendarStringConverter
import io.github.skeptick.libres.compose.painterResource
import java.text.SimpleDateFormat
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun DateTimeView(
    modifier: Modifier,
    selectDate: Calendar,
    currentDate: Calendar? = null,
    back: () -> Unit = {},
    increment: () -> Unit,
    decrement: () -> Unit,
    onOpenDateTimePickerModal: () -> Unit,
    showTitle: Boolean = false
) {
    val backButtonWeight = when {
        currentDate == null -> 0f
        currentDate.date() != selectDate.date() -> 1.5f
        else -> 0f
    }
    Column(modifier = modifier) {
        if (showTitle) {
            Text(
                text = MainRes.string.select_date_tine_title,
                color = LocalCustomColorsPalette.current.secondaryTextAndIcon,
                style = MaterialTheme.typography.h8
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        Row() {
            Button(
                modifier = Modifier.fillMaxHeight().weight(1f).clip(RoundedCornerShape(15.dp)),
                onClick = { decrement() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LocalCustomColorsPalette.current.elevationBackground
                )
            ) {
                Image(
                    modifier = Modifier,
                    painter = painterResource(MainRes.image.arrow_left),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                modifier = Modifier.fillMaxHeight().weight(4f - backButtonWeight)
                    .clip(RoundedCornerShape(15.dp)),
                onClick = { onOpenDateTimePickerModal() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LocalCustomColorsPalette.current.elevationBackground
                )
            ) {
                Text(
                    text = SimpleDateFormat("HH:mm dd MMMM").format(selectDate.time),
                    style = MaterialTheme.typography.h6
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            if (backButtonWeight > 0) {
                Button(
                    modifier = Modifier.fillMaxHeight().weight(backButtonWeight)
                        .clip(RoundedCornerShape(15.dp))
                        .border(3.dp, Color.White, RoundedCornerShape(15.dp)),
                    onClick = back,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = LocalCustomColorsPalette.current.elevationBackground
                    )
                ) {
                    Text(
                        text = SimpleDateFormat("dd MMMM").format(currentDate!!.time),
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
            Button(
                modifier = Modifier.fillMaxHeight().weight(1f).clip(RoundedCornerShape(15.dp)),
                onClick = { increment() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LocalCustomColorsPalette.current.elevationBackground
                )
            ) {
                Image(
                    modifier = Modifier,
                    painter = painterResource(MainRes.image.arrow_right),
                    contentDescription = null
                )
            }
        }
    }
}


private fun Calendar.time() = CalendarStringConverter.calendarToString(this, "HH:mm")
private fun Calendar.date() = CalendarStringConverter.calendarToString(this, "dd MMMM")
private fun Calendar.dateTime() =
    MainRes.string.start_date_time.format(time = time(), date = date())