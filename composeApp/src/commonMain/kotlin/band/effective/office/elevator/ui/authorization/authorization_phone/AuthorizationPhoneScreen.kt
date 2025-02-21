package band.effective.office.elevator.ui.authorization.authorization_phone

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.EffectiveTheme
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.EffectiveButton
import band.effective.office.elevator.components.UserInfoTextField
import band.effective.office.elevator.domain.validator.UserInfoValidator
import band.effective.office.elevator.expects.showToast
import band.effective.office.elevator.textGrayColor
import band.effective.office.elevator.ui.authorization.authorization_phone.store.AuthorizationPhoneStore
import band.effective.office.elevator.ui.authorization.components.AuthSubTitle
import band.effective.office.elevator.ui.authorization.components.AuthTitle
import band.effective.office.elevator.ui.models.PhoneMaskTransformation
import band.effective.office.elevator.ui.models.UserDataTextFieldType
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun AuthorizationPhoneScreen(component: AuthorizationPhoneComponent) {

    val state by component.phone.collectAsState()
    val errorMessage = stringResource(MainRes.strings.number_format_error)

    LaunchedEffect(component) {
        component.label.collect { label ->
            when (label) {
                AuthorizationPhoneStore.Label.AuthorizationPhoneFailure -> {
                    showToast(errorMessage)
                }

                is AuthorizationPhoneStore.Label.AuthorizationPhoneSuccess -> {
                    component.change(state.phoneNumber)
                    component.onOutput(
                        AuthorizationPhoneComponent.Output.OpenProfileScreen
                    )
                }

                AuthorizationPhoneStore.Label.ReturnInGoogleAuthorization -> component.onOutput(
                    AuthorizationPhoneComponent.Output.OpenGoogleScreen
                )
            }
        }
    }

    AuthorizationPhoneComponent(onEvent = component::onEvent, state)
}

@Composable
private fun AuthorizationPhoneComponent(
    onEvent: (AuthorizationPhoneStore.Intent) -> Unit,
    state: AuthorizationPhoneStore.State
) {

    val closeIcon = remember { mutableStateOf(false) }
    val borderColor = remember { mutableStateOf(textGrayColor) }
    val leadingColor = remember { mutableStateOf(textGrayColor) }
    val phoneState =
        if (state.phoneNumber.length > UserInfoValidator.phoneNumberSize)
            state.phoneNumber.substring(
                startIndex = state.phoneNumber.length % UserInfoValidator.phoneNumberSize,
            )
        else state.phoneNumber
    var phoneNumber by remember { mutableStateOf(phoneState) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(EffectiveTheme.colors.background.primary),
    ) {
        IconButton(
            modifier = Modifier.size(size = 48.dp),
            onClick = {
                onEvent(AuthorizationPhoneStore.Intent.BackButtonClicked)
            }
        ) {
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
                text = stringResource(MainRes.strings.input_number),
                textAlign = TextAlign.Center
            )
            AuthSubTitle(
                modifier = Modifier.padding(bottom = 24.dp),
                text = stringResource(MainRes.strings.select_number),
                textAlign = TextAlign.Center,
            )

            UserInfoTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                item = UserDataTextFieldType.Phone,
                error = state.isErrorPhoneNumber,
                visualTransformation = PhoneMaskTransformation,
                text = phoneNumber,
                keyboardType = KeyboardType.Phone,
                onValueChange = {
                    phoneNumber = it
                    onEvent(
                        AuthorizationPhoneStore.Intent.PhoneNumberChanged(phoneNumber = it)
                    )
                }
            )

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
                        onEvent(AuthorizationPhoneStore.Intent.ContinueButtonClicked)
                    }
                )
            }
        }
    }
}
