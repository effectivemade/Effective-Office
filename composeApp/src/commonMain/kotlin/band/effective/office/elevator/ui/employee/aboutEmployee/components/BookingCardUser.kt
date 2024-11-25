package band.effective.office.elevator.ui.employee.aboutEmployee.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.ui.models.ReservedSeat

@Composable
fun BookingCardUser(
    seat: ReservedSeat,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 28.dp, vertical = 16.dp)
    ) {
        Column {
            Text(
                text = seat.seatName,
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight(500),
            )
            Text(
                text = seat.bookingDay,
                color = Color.Gray,
                fontSize = 18.sp,
                fontWeight = FontWeight(500),
            )
        }
    }
}
