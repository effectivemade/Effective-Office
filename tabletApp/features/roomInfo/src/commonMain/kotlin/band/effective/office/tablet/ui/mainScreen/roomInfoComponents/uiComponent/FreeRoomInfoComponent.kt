package band.effective.office.tablet.ui.mainScreen.roomInfoComponents.uiComponent

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.roomInfoColor

@Composable
fun FreeRoomInfoComponent(
    modifier: Modifier = Modifier,
    name: String,
    capacity: Int,
    isHaveTv: Boolean,
    electricSocketCount: Int,
    isError: Boolean,
) {
    CommonRoomInfoComponent(
        modifier = modifier,
        name = name,
        capacity = capacity,
        isHaveTv = isHaveTv,
        electricSocketCount = electricSocketCount,
        backgroundColor = LocalCustomColorsPalette.current.freeStatus,
        isError = isError
    ) {
        Text(
            text = if (isError) MainRes.string.data_not_upd else MainRes.string.free_now,
            style = MaterialTheme.typography.h5,
            color = roomInfoColor
        )
    }
}