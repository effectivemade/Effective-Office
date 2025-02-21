package band.effective.office.elevator.ui.authorization.authorization_telegram

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.EffectiveTheme
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.EffectiveButton
import band.effective.office.elevator.components.UserInfoTextField
import band.effective.office.elevator.expects.showToast
import band.effective.office.elevator.ui.authorization.authorization_telegram.store.AuthorizationTelegramStore
import band.effective.office.elevator.ui.authorization.components.AuthSubTitle
import band.effective.office.elevator.ui.authorization.components.AuthTitle
import band.effective.office.elevator.ui.models.UserDataTextFieldType
import dev.icerock.moko.resources.compose.painterResource
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
    val colors = EffectiveTheme.colors
    val closeIcon = remember { mutableStateOf(false) }
    val borderColor = remember { mutableStateOf(colors.input.freeNormal) }
    val leadingColor = remember { mutableStateOf(colors.text.additional) }
    var telegram by remember { mutableStateOf(state.nick) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(EffectiveTheme.colors.background.primary)
    ) {
        IconButton(
            modifier = Modifier.size(size = 48.dp),
            onClick = {
                onEvent(AuthorizationTelegramStore.Intent.BackButtonClicked)
            }) {
            Icon(
                painter = painterResource(MainRes.images.back_button),
                contentDescription = stringResource(MainRes.strings.back),
                modifier = Modifier.size(size = 24.dp),
                tint = EffectiveTheme.colors.icon.secondary
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AuthTitle(
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                text = stringResource(MainRes.strings.input_employee),
                textAlign = TextAlign.Center
            )
            AuthSubTitle(
                modifier = Modifier.padding(bottom = 24.dp),
                text = stringResource(MainRes.strings.select_number),
                textAlign = TextAlign.Center
            )
            UserInfoTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = telegram,
                item = UserDataTextFieldType.Telegram,
                error = state.isErrorNick,
                keyboardType = KeyboardType.Text,
                onValueChange = {
                    telegram = it
                    onEvent(AuthorizationTelegramStore.Intent.NickChanged(name = it))
                },
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 56.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AuthSubTitle(
                    modifier = Modifier.padding(bottom = 20.dp),
                    text = stringResource(MainRes.strings.button_title),
                    textAlign = TextAlign.Center
                )
                EffectiveButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    buttonText = stringResource(MainRes.strings._continue),
                    onClick = {
                        onEvent(AuthorizationTelegramStore.Intent.ContinueButtonClicked)
                    }
                )
            }
        }
    }
}
