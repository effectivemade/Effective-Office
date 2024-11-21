package band.effective.office.elevator.ui.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.textGrayColor
import band.effective.office.elevator.ui.models.ReservedSeat

@Composable
fun BookingCard(
    seat: ReservedSeat,
    onClickOptionMenu: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            SeatIcon()
            Spacer(modifier = Modifier.width(12.dp))
            SeatTitle(seat)
            Spacer(modifier = Modifier.height(24.dp))
        }
        IconButton(
            onClick = {
                onClickOptionMenu(seat.bookingId)
            },
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clip(RoundedCornerShape(80.dp)),
            colors = IconButtonDefaults.iconButtonColors(),
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "show option menu",
                modifier = Modifier,
                tint = MaterialTheme.colors.secondaryVariant
            )
        }
    }
}

@Composable
fun SeatTitle(seat: ReservedSeat) {
    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = seat.seatName,
            style = MaterialTheme.typography.subtitle1.copy(color = Color.Black)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.wrapContentSize()
        ) {
            Text(
                text = seat.bookingDay,
                style = MaterialTheme.typography.subtitle1.copy(color = textGrayColor),
                modifier = Modifier.wrapContentSize()
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = seat.bookingTime,
                style = MaterialTheme.typography.subtitle1.copy(color = textGrayColor),
                modifier = Modifier.wrapContentSize()
            )
        }
    }
}
