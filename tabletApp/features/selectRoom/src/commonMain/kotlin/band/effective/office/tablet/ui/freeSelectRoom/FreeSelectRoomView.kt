package band.effective.office.tablet.ui.freeSelectRoom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import band.effective.office.tablet.features.selectRoom.MainRes
import band.effective.office.tablet.ui.common.CrossButtonView
import band.effective.office.tablet.ui.freeSelectRoom.store.FreeSelectStore
import band.effective.office.tablet.ui.loader.Loader
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.textButton

@Composable
fun FreeSelectRoomView(freeSelectRoomComponent: FreeSelectRoomComponent) {
    val state by freeSelectRoomComponent.state.collectAsState()
    FreeSelectRoomView(
        onCloseRequest = { freeSelectRoomComponent.sendIntent(FreeSelectStore.Intent.OnCloseWindowRequest) },
        onFreeRoomRequest = { freeSelectRoomComponent.sendIntent(FreeSelectStore.Intent.OnFreeSelectRequest) },
        isLoading = state.isLoad,
        isFail = !state.isSuccess
    )
}


@Composable
fun FreeSelectRoomView(
    onCloseRequest: () -> Unit,
    onFreeRoomRequest: () -> Unit,
    isLoading: Boolean,
    isFail: Boolean
) {
    val shape = RoundedCornerShape(50)

    val isPressed = remember { mutableStateOf(false) }
    val colorButton = if (isPressed.value)
        LocalCustomColorsPalette.current.pressedPrimaryButton else MaterialTheme.colors.primary

    Dialog(
        onDismissRequest = { onCloseRequest() }
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(5))
                .background(LocalCustomColorsPalette.current.elevationBackground),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            CrossButtonView(
                Modifier.width(518.dp).padding(end = 42.dp),
                onDismissRequest = {
                    onCloseRequest()
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
            Box(
                modifier = Modifier.padding(0.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = MainRes.string.free_select_room,
                    style = MaterialTheme.typography.h4,
                    color = LocalCustomColorsPalette.current.primaryTextAndIcon
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                modifier = Modifier.size(290.dp, 64.dp),
                colors = ButtonDefaults.buttonColors(colorButton),
                shape = shape,
                onClick = {
                    isPressed.value = !isPressed.value
                    onFreeRoomRequest()
                }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    when {
                        isLoading -> Loader()
                        isFail -> Text(
                            text = MainRes.string.try_again,
                            style = MaterialTheme.typography.h6,
                            color = textButton,
                        )

                        else -> Text(
                            text = MainRes.string.free_select_room_button,
                            style = MaterialTheme.typography.h6,
                            color = textButton,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}
