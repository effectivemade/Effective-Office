package band.effective.office.tablet.ui.mainScreen.roomInfoComponents

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.theme.CustomColorsPalette
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.utils.CalendarStringConverter
import java.util.Calendar

@Composable
fun FreeRoomInfoComponent(
    modifier: Modifier = Modifier,
    name: String,
    capacity: Int,
    isHaveTv: Boolean,
    electricSocketCount: Int,
    nextEvent: EventInfo?
) {
    CommonRoomInfoComponent(
        modifier = modifier,
        name = name,
        capacity = capacity,
        isHaveTv = isHaveTv,
        electricSocketCount = electricSocketCount,
        roomOccupancy = MainRes.string.free_room_occupancy.format(
            time = nextEvent?.startTime?.time() ?: ""
        ),
        backgroundColor = LocalCustomColorsPalette.current.freeStatus
    )
}

private fun Calendar.time() = CalendarStringConverter.calendarToString(this, "HH:mm")