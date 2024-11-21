package band.effective.office.elevator.ui.employee.aboutEmployee.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ui.main.components.SeatIcon
import band.effective.office.elevator.ui.main.components.SeatTitle
import band.effective.office.elevator.ui.models.ReservedSeat

@Composable
fun BookingCardUser(
    seat: ReservedSeat,
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .fillMaxWidth()
    ) {
        Row {
            SeatIcon()
            Spacer(modifier = Modifier.width(12.dp))
            SeatTitle(seat)
        }
    }
}

