package band.effective.office.tablet.ui.mainScreen.roomInfoComponents

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.ui.bookingComponents.DateTimeView
import band.effective.office.tablet.ui.mainScreen.roomInfoComponents.uiComponent.BusyRoomInfoComponent
import band.effective.office.tablet.ui.mainScreen.roomInfoComponents.uiComponent.FreeRoomInfoComponent
import java.util.Calendar
import java.util.GregorianCalendar

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun RoomInfoComponent(
    modifier: Modifier = Modifier,
    room: RoomInfo,
    onOpenFreeRoomModalRequest: () -> Unit,
    timeToNextEvent: Int,
    isError: Boolean,
) {
    val paddings = 30.dp
    Column(modifier = modifier) {
        when {
            room.isFree() -> {
                FreeRoomInfoComponent(
                    modifier = Modifier.padding(paddings),
                    name = room.name,
                    capacity = room.capacity,
                    isHaveTv = room.isHaveTv,
                    electricSocketCount = room.socketCount,
                    isError = isError
                )
            }

            room.isBusy() -> {
                BusyRoomInfoComponent(
                    modifier = Modifier.padding(paddings),
                    name = room.name,
                    capacity = room.capacity,
                    isHaveTv = room.isHaveTv,
                    electricSocketCount = room.socketCount,
                    event = room.currentEvent ?: EventInfo.emptyEvent,
                    onButtonClick = { onOpenFreeRoomModalRequest() },
                    timeToFinish = timeToNextEvent,
                    isError = isError
                )
            }
        }
    }
}

private fun RoomInfo.isFree() = currentEvent == null
private fun RoomInfo.isBusy() = currentEvent != null
