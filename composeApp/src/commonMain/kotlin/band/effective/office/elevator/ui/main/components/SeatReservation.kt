package band.effective.office.elevator.ui.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.buttons.EffectiveButton
import band.effective.office.elevator.textGrayColor
import band.effective.office.elevator.ui.models.ReservedSeat
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun SeatsReservation(
    reservedSeats: List<ReservedSeat>,
    onClickBook: () -> Unit,
    onClickOptionMenu: (String) -> Unit,
) {
    when(reservedSeats.isEmpty()) {
        true -> EmptyReservation(onClickBook)
        false -> NonEmptyReservation(
            reservedSeats = reservedSeats,
            onClickOptionMenu = onClickOptionMenu
        )
    }
}

@Composable
fun EmptyReservation(onClickBook: () -> Unit) {
    val scrollState = rememberScrollState() // NOTE: this need, what pull up to refresh worked
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(MainRes.strings.none_booking_seat),
            fontSize = 15.sp,
            color = textGrayColor
        )
        Spacer(modifier = Modifier.height(16.dp))
        EffectiveButton(
            buttonText = stringResource(MainRes.strings.book_a_seat),
            onClick = onClickBook,
            modifier = Modifier.width(280.dp),
        )
    }
}

@Composable
fun NonEmptyReservation(
    reservedSeats: List<ReservedSeat>,
    onClickOptionMenu: (String) -> Unit
) {
    LazyColumn (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        items(reservedSeats) { seat ->
            BookingCard(
                seat = seat,
                onClickOptionMenu = onClickOptionMenu,
            )
        }
    }
}
