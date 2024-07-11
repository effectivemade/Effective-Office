package band.effective.office.tablet.ui.fastEvent

import androidx.compose.foundation.background
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
import band.effective.office.tablet.ui.common.CrossButtonView
import band.effective.office.tablet.ui.fastEvent.store.FastEventStore
import band.effective.office.tablet.ui.loader.Loader
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.uiComponents.failureBooking.FailureFastSelectRoomView
import band.effective.office.tablet.ui.uiComponents.successBooking.SuccessFastSelectRoomView
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import java.text.SimpleDateFormat

@Composable
fun FastEventView(
    component: FastEventComponent
) {
    val state by component.state.collectAsState()
    Children(stack = component.childStack, modifier = Modifier.padding(35.dp)) {
        Dialog(
            onDismissRequest = { component.sendIntent(FastEventStore.Intent.OnCloseWindowRequest)},
            properties = DialogProperties(
                usePlatformDefaultWidth = it.instance != FastEventComponent.ModalConfig.FailureModal
            )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    text = SimpleDateFormat("HH:mm").format(state.currentTime),
                    style = MaterialTheme.typography.h2,
                    color = LocalCustomColorsPalette.current.primaryTextAndIcon
                )
                Row(
                    modifier = Modifier.fillMaxHeight(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    when (it.instance) {
                        FastEventComponent.ModalConfig.LoadingModal -> FastEventView(
                            onDismissRequest = { component.sendIntent(FastEventStore.Intent.OnCloseWindowRequest) }
                        )

                        FastEventComponent.ModalConfig.FailureModal -> FailureFastSelectRoomView(
                            onDismissRequest = { component.sendIntent(FastEventStore.Intent.OnCloseWindowRequest) },
                            minutes = state.minutesLeft,
                            room = component.room
                        )

                        FastEventComponent.ModalConfig.SuccessModal -> SuccessFastSelectRoomView(
                            roomName = component.room,
                            eventInfo = component.eventInfo,
                            close = { component.sendIntent(FastEventStore.Intent.OnCloseWindowRequest) },
                            onFreeRoomRequest = { component.sendIntent(FastEventStore.Intent.OnFreeSelectRequest) },
                            isLoading = state.isLoad
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FastEventView(
    onDismissRequest:() -> Unit
) {
    Box(contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .fillMaxHeight(0.4f)
                .clip(RoundedCornerShape(3))
                .background(LocalCustomColorsPalette.current.elevationBackground)
                .padding(35.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CrossButtonView(
                Modifier.fillMaxWidth(),
                onDismissRequest = { onDismissRequest() }
            )
            Spacer(modifier = Modifier.height(40.dp))
            Loader()
        }
    }
}