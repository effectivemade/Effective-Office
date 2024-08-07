package band.effective.office.tablet.ui.mainScreen.slotComponent

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.domain.model.Slot
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.mainScreen.slotComponent.component.BorderIndicator
import band.effective.office.tablet.ui.mainScreen.slotComponent.model.SlotUi
import band.effective.office.tablet.ui.mainScreen.slotComponent.store.SlotStore
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.h7
import band.effective.office.tablet.ui.theme.subslotColor
import org.koin.core.component.getScopeId
import java.text.SimpleDateFormat
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun SlotList(component: SlotComponent) {
    val state by component.state.collectAsState()
    SlotList(
        slots = state.slots,
        onClick = { component.sendIntent(SlotStore.Intent.ClickOnSlot(this)) },
        onCancel = { component.sendIntent(SlotStore.Intent.OnCancelDelete(it)) },
    )
}

@Composable
private fun SlotList(
    slots: List<SlotUi>,
    onClick: SlotUi.() -> Unit,
    onCancel: (SlotUi.DeleteSlot) -> Unit,
) {
    LazyColumn(
        Modifier.padding(start = 30.dp, top = 0.dp, end = 30.dp, bottom = 30.dp),
    ) {
        itemsIndexed(items = slots) { id, slot ->
            SlotView(slotUi = slot, onClick = onClick, onCancel = onCancel)
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
fun SlotView(
    slotUi: SlotUi,
    onClick: SlotUi.() -> Unit,
    onCancel: (SlotUi.DeleteSlot) -> Unit
) {
    val borderShape = CircleShape
    val itemModifier = Modifier
        .fillMaxWidth()
        .clip(borderShape)
        .background(MaterialTheme.colors.surface)
        .clickable { slotUi.onClick() }
        .run {
            if (slotUi !is SlotUi.DeleteSlot) border(
                width = 5.dp,
                color = slotUi.borderColor(),
                shape = borderShape
            ).padding(vertical = 15.dp, horizontal = 30.dp) else this
        }

    when (slotUi) {
        is SlotUi.DeleteSlot -> DeletedSlotView(
            modifier = itemModifier,
            slotUi = slotUi,
            onCancel = onCancel,
            paddingValues = PaddingValues(vertical = 15.dp, horizontal = 30.dp)
        )

        is SlotUi.MultiSlot -> MultiSlotView(
            modifier = itemModifier,
            slotUi = slotUi,
            onItemClick = { item -> item.onClick() },
            onCancel = onCancel
        )

        is SlotUi.SimpleSlot -> CommonSlotView(
            modifier = itemModifier,
            slotUi = slotUi
        )

        is SlotUi.NestedSlot -> CommonSlotView(
            modifier = itemModifier,
            slotUi = slotUi
        )

        is SlotUi.LoadingSlot -> LoadingSlotView(
            modifier = itemModifier,
            slotUi = slotUi
        )
    }
}

@Composable
private fun DeletedSlotView(
    modifier: Modifier = Modifier,
    slotUi: SlotUi.DeleteSlot,
    onCancel: (SlotUi.DeleteSlot) -> Unit,
    paddingValues: PaddingValues
) {
    Box(modifier = modifier.height(IntrinsicSize.Min).width(IntrinsicSize.Min)) {
        var isCancelability by remember { mutableStateOf(true) }
        CommonSlotView(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth(),
            slotUi = slotUi
        ) {
            if (isCancelability) {
                Text(
                    modifier = Modifier.clickable { onCancel(slotUi) },
                    text = MainRes.string.cancel,
                    style = MaterialTheme.typography.h7,
                    color = Color.White
                )
            }
        }
        BorderIndicator(onDispose = {
            slotUi.onDelete()
            isCancelability = false
        }, stokeWidth = 10.dp)
    }
}

@Composable
private fun MultiSlotView(
    modifier: Modifier = Modifier,
    slotUi: SlotUi.MultiSlot,
    onItemClick: (SlotUi) -> Unit,
    onCancel: (SlotUi.DeleteSlot) -> Unit
) {
    Column(Modifier.animateContentSize()) {
        CommonSlotView(modifier, slotUi) {
            val rotateDegrees = if (slotUi.isOpen) 180f else 0f
            Image(
                modifier = Modifier.fillMaxHeight().rotate(rotateDegrees),
                painter = painterResource(MainRes.image.arrow_to_down),
                contentDescription = null
            )
        }
        if (slotUi.isOpen) {
            slotUi.subSlots.forEach {
                Spacer(Modifier.height(20.dp))
                SlotView(
                    slotUi = it,
                    onClick = onItemClick,
                    onCancel = onCancel
                )
            }
        }
    }
}

@Composable
private fun CommonSlotView(
    modifier: Modifier = Modifier,
    slotUi: SlotUi,
    content: @Composable () -> Unit = {}
) {
    val slot = slotUi.slot
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "${slot.start.toFormattedString("HH:mm")} - ${slot.finish.toFormattedString("HH:mm")}",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onPrimary
            )
            Text(
                text = slot.subtitle(),
                style = MaterialTheme.typography.h7,
                color = Color.White
            )
        }
        content()
    }
}

@Composable
private fun LoadingSlotView(
    modifier: Modifier = Modifier,
    slotUi: SlotUi,
    content: @Composable () -> Unit = {}
) {
    val slot = slotUi.slot
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = MainRes.string.loading_slot_for_time.format(
                    slot.start.toFormattedString("HH:mm"),
                    slot.finish.toFormattedString("HH:mm")
                ),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onPrimary
            )
            Text(
                text = slot.subtitle(),
                style = MaterialTheme.typography.h7,
                color = Color.White
            )
        }
        content()
    }
}

@Composable
private fun SlotUi.borderColor() = when (this) {
    is SlotUi.DeleteSlot -> Color.Gray
    is SlotUi.MultiSlot -> LocalCustomColorsPalette.current.busyStatus
    is SlotUi.NestedSlot -> subslotColor
    is SlotUi.SimpleSlot -> when (slot) {
        is Slot.EmptySlot -> LocalCustomColorsPalette.current.freeStatus
        is Slot.EventSlot -> LocalCustomColorsPalette.current.busyStatus
        is Slot.MultiEventSlot -> LocalCustomColorsPalette.current.busyStatus
        is Slot.LoadingEventSlot -> LocalCustomColorsPalette.current.loadingColor
    }

    is SlotUi.LoadingSlot -> LocalCustomColorsPalette.current.loadingColor
}

private fun Slot.subtitle() = when (this) {
    is Slot.EmptySlot -> MainRes.string.empty_slot.format(freeTime(this).toString())
    is Slot.EventSlot -> MainRes.string.event_slot.format(eventInfo.organizer.fullName)
    is Slot.MultiEventSlot -> MainRes.string.multislot.format(events.size.toString())
    is Slot.LoadingEventSlot -> MainRes.string.loading_slot.format(eventInfo.organizer.fullName)
}

fun freeTime(slot: Slot): Int {
    val timeInMillis = slot.finish.timeInMillis - slot.start.timeInMillis
    return when {
        timeInMillis > 0 -> (timeInMillis / 60000).toInt()
        else -> 0
    }
}

private fun Calendar.toFormattedString(pattern: String) = SimpleDateFormat(pattern).format(time)