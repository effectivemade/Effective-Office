package band.effective.office.tablet.ui.uiComponents.successBooking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.features.selectRoom.MainRes
import band.effective.office.tablet.ui.common.CrossButtonView
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.uiComponents.FreeBookingButtonView
import band.effective.office.tablet.ui.uiComponents.successBooking.uiComponents.IconSuccess
import band.effective.office.tablet.ui.uiComponents.successBooking.uiComponents.SuccessText
import band.effective.office.tablet.utils.time24

@Composable
fun SuccessFastSelectRoomView(
    roomName: String,
    eventInfo: EventInfo,
    close: () -> Unit,
    onFreeRoomRequest: () -> Unit,
    isLoading: Boolean
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(3))
            .background(LocalCustomColorsPalette.current.elevationBackground)
            .padding(top = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CrossButtonView(
            Modifier
                .fillMaxWidth()
                .padding(end = 42.dp),
            onDismissRequest = { close() }
        )
        Spacer(modifier = Modifier.height(2.dp))
        IconSuccess()
        Spacer(modifier = Modifier.height(24.dp))
        SuccessText(
            modifier = Modifier.fillMaxWidth(),
            nameRoom = roomName
        )
        Spacer(modifier = Modifier.height(50.dp))
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = MainRes.string.booked_until.format(
                    finishTime = eventInfo.finishTime.time24()
                ),
                style = MaterialTheme.typography.h5,
                color = LocalCustomColorsPalette.current.primaryTextAndIcon
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Spacer(modifier = Modifier.height(30.dp))
        FreeBookingButtonView(
            modifier = Modifier
                .fillMaxWidth(0.72f)
                .height(64.dp),
            shape = RoundedCornerShape(100),
            text = MainRes.string.cancel_book,
            onClick = {
                onFreeRoomRequest()
            },
            isLoading = isLoading
        )
        Spacer(Modifier.height(30.dp))
    }
}