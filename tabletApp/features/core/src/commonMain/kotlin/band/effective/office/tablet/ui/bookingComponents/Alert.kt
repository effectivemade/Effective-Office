package band.effective.office.tablet.ui.bookingComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.ui.theme.alertColor

@Composable
fun Alert(modifier: Modifier = Modifier, text: String) {
    Box(
        modifier = modifier.background(
            color = alertColor,
            shape = RoundedCornerShape(15.dp)
        ).padding(horizontal = 10.dp, vertical = 5.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text = text, style = MaterialTheme.typography.h6)
    }
}