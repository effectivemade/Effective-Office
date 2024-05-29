package band.effective.office.tablet.ui.uiComponents.successBooking.uiComponents

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette

@Composable
fun OrganizerEventView(organizer: String) {
    Text(
        text = organizer,
        style = MaterialTheme.typography.h5,
        color = LocalCustomColorsPalette.current.primaryTextAndIcon
    )
}