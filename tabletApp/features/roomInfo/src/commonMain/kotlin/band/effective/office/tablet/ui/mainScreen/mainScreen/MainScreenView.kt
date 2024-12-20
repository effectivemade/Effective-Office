package band.effective.office.tablet.ui.mainScreen.mainScreen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.bookingComponents.DateTimeView
import band.effective.office.tablet.ui.mainScreen.mainScreen.uiComponents.Disconnect
import band.effective.office.tablet.ui.mainScreen.roomInfoComponents.RoomInfoComponent
import band.effective.office.tablet.ui.mainScreen.roomInfoComponents.uiComponent.RoomProperty
import band.effective.office.tablet.ui.mainScreen.slotComponent.SlotComponent
import band.effective.office.tablet.ui.mainScreen.slotComponent.SlotView
import band.effective.office.tablet.ui.mainScreen.slotComponent.store.SlotStore
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.textButton
import java.util.Calendar
import java.util.GregorianCalendar

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("NewApi", "StateFlowValueCalledInComposition", "UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun MainScreenView(
    slotComponent: SlotComponent,
    isDisconnect: Boolean,
    roomList: List<RoomInfo>,
    indexSelectRoom: Int,
    timeToNextEvent: Int,
    onRoomButtonClick: (Int) -> Unit,
    onCancelEventRequest: () -> Unit,
    onFastBooking: (Int) -> Unit,
    onUpdate: () -> Unit,
    onOpenDateTimePickerModalRequest: () -> Unit,
    onIncrementData: () -> Unit,
    onDecrementData: () -> Unit,
    selectDate: Calendar,
    onResetDate: () -> Unit
) {
    val slotState by slotComponent.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colors.background)) {
        /*NOTE(Maksim Mishenko):
        * infoViewWidth is part of the width occupied by roomInfoView
        * infoViewWidth = infoViewFrame.width / mainScreenFrame.width
        * where infoViewFrame, mainScreenFrame is frames from figma and all width I get from figma*/
        val infoViewWidth = 627f / 1133f
        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(infoViewWidth)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth().
                        padding(bottom = 30.dp)) {
                    item {
                        DateTimeView(
                            modifier = Modifier.padding(
                                start = 30.dp,
                                top = 50.dp,
                                end = 20.dp,
                                bottom = 0.dp
                            ).height(70.dp),
                            selectDate = selectDate,
                            increment = onIncrementData,
                            decrement = onDecrementData,
                            onOpenDateTimePickerModal = onOpenDateTimePickerModalRequest,
                            currentDate = GregorianCalendar(),
                            back = onResetDate,
                        )
                    }
                    stickyHeader {
                            RoomInfoComponent(
                                modifier = Modifier
                                    .background(color = MaterialTheme.colors.background),
                                room = roomList[indexSelectRoom],
                                onOpenFreeRoomModalRequest = { onCancelEventRequest() },
                                timeToNextEvent = timeToNextEvent,
                                isError = isDisconnect,
                            )
                    }
                    items(slotState.slots) {
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 30.dp)) {
                            SlotView(
                                slotUi = it,
                                onClick = {
                                    slotComponent.sendIntent(
                                        SlotStore.Intent.ClickOnSlot(
                                            this
                                        )
                                    )
                                },
                                onCancel = {
                                    slotComponent.sendIntent(
                                        SlotStore.Intent.OnCancelDelete(
                                            it
                                        )
                                    )
                                }
                            )
                        }
                        Spacer(Modifier.height(20.dp))
                    }
                }
                Box(modifier = Modifier.padding(horizontal = 30.dp)) {
                    Disconnect(visible = isDisconnect)
                }
            }
            Row {
                Spacer(Modifier.fillMaxWidth(0.15f))
                Column(
                    modifier = Modifier.fillMaxHeight().padding(
                        start = 0.dp,
                        top = 40.dp,
                        end = 40.dp,
                        bottom = 40.dp
                    ),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    FastBookingButtons(onBooking = onFastBooking)
                    RoomList(
                        list = roomList,
                        indexSelectRoom = indexSelectRoom,
                        onClick = onRoomButtonClick
                    )
                }
            }
        }
    }
}

@Composable
fun FastBookingButtons(onBooking: (Int) -> Unit) {
    Column {
        Text(
            text = MainRes.string.fastbooking_title,
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.h5
        )
        Spacer(Modifier.height(10.dp))
        Row {
            val buttonModifier = Modifier.fillMaxWidth().weight(1f)
            listOf(15, 30, 60).forEachIndexed { index, time ->
                if (index != 0) {
                    Spacer(Modifier.width(10.dp))
                }
                Button(
                    modifier = buttonModifier,
                    onClick = { onBooking(time) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = textButton)
                ) {
                    Text(
                        text = MainRes.string.fastbooking_button.format(time.toString()),
                        color = Color.Black,
                    )
                }
            }
        }
    }
}

@Composable
fun RoomList(list: List<RoomInfo>, indexSelectRoom: Int, onClick: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        list.forEachIndexed() { index, roomInfo ->
            RoomButton(
                modifier = Modifier
                    .background(
                        color = if (index == indexSelectRoom) MaterialTheme.colors.surface else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable { onClick(index) }
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .padding(10.dp),
                room = roomInfo
            )
            Spacer(Modifier.height(5.dp))
        }

    }
}

@Composable
fun RoomButton(modifier: Modifier, room: RoomInfo) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .height(10.dp)
                    .width(10.dp)
                    .background(
                        color = if (room.currentEvent == null) LocalCustomColorsPalette.current.freeStatus else LocalCustomColorsPalette.current.busyStatus,
                        shape = CircleShape
                    )
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = room.name,
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h5
            )
        }
        RoomProperty(
            spaceBetweenProperty = 20.dp,
            capacity = room.capacity,
            isHaveTv = room.isHaveTv,
            electricSocketCount = room.socketCount
        )
    }
}