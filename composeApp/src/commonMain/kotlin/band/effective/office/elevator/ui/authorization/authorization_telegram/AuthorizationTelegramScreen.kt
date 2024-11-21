package band.effective.office.elevator.ui.authorization.authorization_telegram

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.EffectiveButton
import band.effective.office.elevator.components.OutlinedTextColorsSetup
import band.effective.office.elevator.components.UserInfoTextField
import band.effective.office.elevator.expects.showToast
import band.effective.office.elevator.textGrayColor
import band.effective.office.elevator.ui.authorization.authorization_profile.store.AuthorizationProfileStore
import band.effective.office.elevator.ui.authorization.authorization_telegram.store.AuthorizationTelegramStore
import band.effective.office.elevator.ui.authorization.components.AuthTabRow
import band.effective.office.elevator.ui.authorization.components.AuthTitle
import band.effective.office.elevator.ui.models.UserDataTextFieldType
import dev.icerock.moko.resources.compose.stringResource


@Composable
fun AuthorizationTelegramScreen(component: AuthorizationTelegramComponent) {

    val state by component.nick.collectAsState()
    val errorMessage = stringResource(MainRes.strings.telegram_format_error)

    LaunchedEffect(component) {
        component.label.collect { label ->
            when (label) {
                AuthorizationTelegramStore.Label.AuthorizationTelegramFailure -> {
                    showToast(errorMessage)
                }

                is AuthorizationTelegramStore.Label.AuthorizationTelegramSuccess -> {
                    component.changeTG(state.nick)
                    component.onOutput(
                        AuthorizationTelegramComponent.Output.OpenContentFlow
                    )
                }

                is AuthorizationTelegramStore.Label.ReturnInProfileAuthorization -> component.onOutput(
                    AuthorizationTelegramComponent.Output.OpenProfileScreen
                )
            }
        }
    }

    AuthorizationTelegramComponent(onEvent = component::onEvent, state)
}

@Composable
private fun AuthorizationTelegramComponent(
    onEvent: (AuthorizationTelegramStore.Intent) -> Unit,
    state: AuthorizationTelegramStore.State
) {
    val closeIcon = remember { mutableStateOf(false) }
    val borderColor = remember { mutableStateOf(textGrayColor) }
    val leadingColor = remember { mutableStateOf(textGrayColor) }
    var telegram by remember { mutableStateOf(state.nick) }

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(
                horizontal = 16.dp,
                vertical = 48.dp
            ),
    ) {
        IconButton(
            modifier = Modifier.size(size = 48.dp),
            onClick = {
                onEvent(AuthorizationTelegramStore.Intent.BackButtonClicked)
            }) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                tint = ExtendedThemeColors.colors.blackColor,
                contentDescription = "back screen arrow"
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        AuthTabRow(2)

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            AuthTitle(
                text = stringResource(MainRes.strings.input_employee),
                modifier = Modifier.padding(bottom = 7.dp),
                textAlign = TextAlign.Start
            )

            UserInfoTextField(
                text = telegram,
                item = UserDataTextFieldType.Telegram,
                error = state.isErrorNick,
                keyboardType = KeyboardType.Text,
                onValueChange = {
                    if (it.isNotEmpty()) {
                        closeIcon.value = true
                        leadingColor.value = Color.Black
                        borderColor.value = ExtendedThemeColors.colors.trinidad_400
                    } else {
                        borderColor.value = textGrayColor
                        closeIcon.value = false
                        leadingColor.value = textGrayColor
                    }
                    telegram = it
                    onEvent(AuthorizationTelegramStore.Intent.NickChanged(name = it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .onFocusChanged {
                        if (it.isFocused) {
                            borderColor.value = ExtendedThemeColors.colors.trinidad_400
                        } else {
                            borderColor.value = textGrayColor
                            leadingColor.value = textGrayColor
                        }
                    }
            )

            Spacer(modifier = Modifier.height(16.dp))

            EffectiveButton(
                buttonText = stringResource(MainRes.strings._continue),
                onClick =  {
                    onEvent(AuthorizationTelegramStore.Intent.ContinueButtonClicked)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}