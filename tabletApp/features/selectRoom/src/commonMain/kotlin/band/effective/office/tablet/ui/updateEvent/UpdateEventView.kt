package band.effective.office.tablet.ui.updateEvent

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import band.effective.office.tablet.domain.model.Organizer
import band.effective.office.tablet.features.selectRoom.MainRes
import band.effective.office.tablet.ui.bookingComponents.DateTimeView
import band.effective.office.tablet.ui.bookingComponents.EventDurationView
import band.effective.office.tablet.ui.bookingComponents.EventOrganizerView
import band.effective.office.tablet.ui.bookingComponents.pickerDateTime.DateTimePickerModalView
import band.effective.office.tablet.ui.buttons.alert.AlertButton
import band.effective.office.tablet.ui.buttons.success.SuccessButton
import band.effective.office.tablet.ui.common.CrossButtonView
import band.effective.office.tablet.ui.loader.Loader
import band.effective.office.tablet.ui.uiComponents.failureBooking.FailureSelectRoomView
import band.effective.office.tablet.ui.uiComponents.successBooking.SuccessSelectRoomView
import band.effective.office.tablet.ui.updateEvent.store.UpdateEventStore
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import java.text.SimpleDateFormat
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UpdateEventView(
    component: UpdateEventComponent
) {
    val state by component.state.collectAsState()

    if (state.showSelectDate) {
        DateTimePickerModalView(
            dateTimePickerComponent = component.dateTimePickerComponent
        )
    } else {
        Children(stack = component.childStack, modifier = Modifier.padding(35.dp)) {
            Dialog(
                onDismissRequest = { component.sendIntent(UpdateEventStore.Intent.OnClose) },
                properties = DialogProperties(
                    usePlatformDefaultWidth = it.instance != UpdateEventComponent.ModalConfig.FailureModal
                )
            ) {
                when (it.instance) {
                    UpdateEventComponent.ModalConfig.FailureModal -> FailureSelectRoomView(
                        onDismissRequest = { component.sendIntent(UpdateEventStore.Intent.OnClose) })

                    UpdateEventComponent.ModalConfig.SuccessModal -> SuccessSelectRoomView(
                        roomName = component.room,
                        organizerName = state.selectOrganizer.fullName,
                        eventInfo = state.event,
                        close = { component.sendIntent(UpdateEventStore.Intent.OnClose) }
                    )

                    UpdateEventComponent.ModalConfig.UpdateModal -> UpdateEventView(
                        onDismissRequest = { component.sendIntent(UpdateEventStore.Intent.OnClose) },
                        incrementData = {
                            component.sendIntent(
                                UpdateEventStore.Intent.OnUpdateDate(
                                    1
                                )
                            )
                        },
                        decrementData = {
                            component.sendIntent(
                                UpdateEventStore.Intent.OnUpdateDate(
                                    -1
                                )
                            )
                        },
                        onOpenDateTimePickerModal = { component.sendIntent(UpdateEventStore.Intent.OnOpenSelectDateDialog) },
                        incrementDuration = {
                            component.sendIntent(UpdateEventStore.Intent.OnUpdateLength(30))
                        },
                        decrementDuration = {
                            component.sendIntent(UpdateEventStore.Intent.OnUpdateLength(-15))
                        },
                        onExpandedChange = { component.sendIntent(UpdateEventStore.Intent.OnExpandedChange) },
                        onSelectOrganizer = {
                            component.sendIntent(UpdateEventStore.Intent.OnSelectOrganizer(it))
                        },
                        selectData = state.date,
                        selectDuration = state.duration,
                        selectOrganizer = state.selectOrganizer,
                        organizers = state.selectOrganizers,
                        expended = state.expanded,
                        onUpdateEvent = {
                            component.sendIntent(UpdateEventStore.Intent.OnUpdateEvent(component.room))
                        },
                        onDeleteEvent = { component.sendIntent(UpdateEventStore.Intent.OnDeleteEvent) },
                        inputText = state.inputText,
                        onInput = { component.sendIntent(UpdateEventStore.Intent.OnInput(it)) },
                        onDoneInput = { component.sendIntent(UpdateEventStore.Intent.OnDoneInput) },
                        isUpdateError = state.isErrorUpdate,
                        isUpdateLoad = state.isLoadUpdate,
                        isDeleteError = state.isErrorDelete,
                        isDeleteLoad = state.isLoadDelete,
                        enableUpdateButton = state.enableUpdateButton,
                        isNewEvent = !state.isCreatedEvent(),
                        onCreateEvent = {
                            component.sendIntent(UpdateEventStore.Intent.OnBooking)
                        },
                        start = state.event.startTime.format("HH:mm"),
                        finish = state.event.finishTime.format("HH:mm"),
                        room = component.room
                    )
                }
            }
        }
    }
}

private fun Calendar.format(template: String): String =
    SimpleDateFormat(template).format(time)


@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun UpdateEventView(
    onDismissRequest: () -> Unit,
    incrementData: () -> Unit,
    decrementData: () -> Unit,
    onOpenDateTimePickerModal: () -> Unit,
    incrementDuration: () -> Unit,
    decrementDuration: () -> Unit,
    onExpandedChange: () -> Unit,
    onSelectOrganizer: (Organizer) -> Unit,
    selectData: Calendar,
    selectDuration: Int,
    selectOrganizer: Organizer,
    organizers: List<Organizer>,
    expended: Boolean,
    onCreateEvent: () -> Unit,
    onUpdateEvent: () -> Unit,
    onDeleteEvent: () -> Unit,
    inputText: String,
    onInput: (String) -> Unit,
    onDoneInput: (String) -> Unit,
    isUpdateError: Boolean,
    isUpdateLoad: Boolean,
    isDeleteError: Boolean,
    isDeleteLoad: Boolean,
    enableUpdateButton: Boolean,
    isNewEvent: Boolean,
    start: String,
    finish: String,
    room: String
) {

    Box {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(3))
                .background(MaterialTheme.colors.background)
                .padding(35.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isNewEvent) MainRes.string.create_view_title.format(room) else MainRes.string.booking_view_title,
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(15.dp))
            DateTimeView(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                selectDate = selectData,
                increment = incrementData,
                decrement = decrementData,
                onOpenDateTimePickerModal = onOpenDateTimePickerModal,
                showTitle = true
            )
            Spacer(modifier = Modifier.height(15.dp))
            EventDurationView(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                currentDuration = selectDuration,
                increment = incrementDuration,
                decrement = decrementDuration
            )
            Spacer(modifier = Modifier.height(15.dp))
            EventOrganizerView(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                selectOrganizers = organizers,
                expanded = expended,
                selectedItem = selectOrganizer,
                onExpandedChange = onExpandedChange,
                onSelectItem = onSelectOrganizer,
                onInput = onInput,
                onDoneInput = onDoneInput,
                inputText = inputText
            )
            Spacer(modifier = Modifier.height(25.dp))
            if (isNewEvent) {
                SuccessButton(
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    onClick = onCreateEvent,
                    enable = enableUpdateButton
                ) {
                    when {
//                        isUpdateLoad -> Loader()
                        isUpdateError -> Text(
                            text = MainRes.string.try_again,
                            style = MaterialTheme.typography.h6
                        )

                        else -> {
                            Text(
                                text = MainRes.string.booking_time_button.format(
                                    startTime = start,
                                    finishTime = finish
                                ),
                                style = MaterialTheme.typography.h6
                            )
                        }
                    }
                }
            } else {
                SuccessButton(
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    onClick = onUpdateEvent,
                    enable = enableUpdateButton
                ) {
                    when {
//                        isUpdateLoad -> Loader()
                        isUpdateError -> Text(
                            text = MainRes.string.try_again,
                            style = MaterialTheme.typography.h6
                        )

                        else -> {
                            Text(
                                text = MainRes.string.update_button,
                                style = MaterialTheme.typography.h6
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                AlertButton(
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    onClick = onDeleteEvent
                ) {
                    when {
                        isDeleteLoad -> Loader()
                        isDeleteError -> Text(
                            text = MainRes.string.update_button,
                            style = MaterialTheme.typography.h6
                        )

                        else -> {
                            Text(
                                text = MainRes.string.delete_button,
                                style = MaterialTheme.typography.h6
                            )
                        }
                    }
                }
            }
        }
        CrossButtonView(
            Modifier
                .fillMaxWidth().padding(35.dp),
            onDismissRequest = onDismissRequest
        )
    }

}
