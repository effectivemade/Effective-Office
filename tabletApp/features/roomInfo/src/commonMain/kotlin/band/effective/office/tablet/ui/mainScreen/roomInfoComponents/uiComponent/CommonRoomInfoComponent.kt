package band.effective.office.tablet.ui.mainScreen.roomInfoComponents.uiComponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.theme.h8
import band.effective.office.tablet.ui.theme.roomInfoColor
import band.effective.office.tablet.ui.theme.undefineStateColor
import io.github.skeptick.libres.compose.painterResource
import io.github.skeptick.libres.images.Image

@Composable
fun CommonRoomInfoComponent(
    modifier: Modifier = Modifier,
    name: String,
    capacity: Int,
    isHaveTv: Boolean,
    electricSocketCount: Int,
    backgroundColor: Color,
    isError: Boolean,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .padding(20.dp)
            .clip(RoundedCornerShape(40.dp))
            .background(color = if (isError) undefineStateColor else backgroundColor)
            .fillMaxWidth()
    ) {
        Column(modifier) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                val spaceBetweenProperty = 20.dp
                Text(
                    text = name,
                    style = MaterialTheme.typography.h1,
                    color = roomInfoColor
                )
                Spacer(modifier = Modifier.width(spaceBetweenProperty))
                RoomProperty(
                    spaceBetweenProperty = spaceBetweenProperty,
                    capacity = capacity,
                    isHaveTv = isHaveTv,
                    electricSocketCount = electricSocketCount
                )
            }
            content()
        }
    }
}

@Composable
fun RoomProperty(
    spaceBetweenProperty: Dp,
    capacity: Int,
    isHaveTv: Boolean,
    electricSocketCount: Int
) {
    Row {
        RoomPropertyComponent(
            image = MainRes.image.quantity,
            text = "$capacity",
            color = roomInfoColor
        )
        if (isHaveTv) {
            Spacer(modifier = Modifier.width(spaceBetweenProperty))
            RoomPropertyComponent(
                image = MainRes.image.tv,
                text = MainRes.string.tv_property,
                color = roomInfoColor
            )
        }
        if (electricSocketCount > 0) {
            Spacer(modifier = Modifier.width(spaceBetweenProperty))
            RoomPropertyComponent(
                image = MainRes.image.ethernet,
                text = "$electricSocketCount",
                color = roomInfoColor
            )
        }
    }
}

@Composable
fun RoomPropertyComponent(image: Image, text: String, color: Color) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier,
            painter = painterResource(image),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.h8)
    }
}