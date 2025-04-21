package band.effective.office.elevator.ui.authorization.authorization_profile

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
import band.effective.office.elevator.components.buttons.EffectiveButton
import band.effective.office.elevator.components.UserInfoTextField
import band.effective.office.elevator.expects.showToast
import band.effective.office.elevator.textGrayColor
import band.effective.office.elevator.ui.authorization.authorization_profile.store.AuthorizationProfileStore
import band.effective.office.elevator.ui.authorization.components.AuthSubTitle
import band.effective.office.elevator.ui.authorization.components.AuthTitle
import band.effective.office.elevator.ui.models.UserDataTextFieldType
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun AuthorizationProfileScreen(component: AuthorizationProfileComponent) {

    val state by component.user.collectAsState()
    val errorMessage = stringResource(MainRes.strings.profile_format_error)

    LaunchedEffect(component) {
        component.label.collect { label ->
            when (label) {
                AuthorizationProfileStore.Label.AuthorizationProfileFailure -> {
                    showToast(errorMessage)
                }

                is AuthorizationProfileStore.Label.AuthorizationProfileSuccess -> {

                    component.changeUserName(state.name)
                    component.changeUserPost(state.post)

                    component.onOutput(
                        AuthorizationProfileComponent.Output.OpenTGScreen
                    )
                }

                is AuthorizationProfileStore.Label.ReturnInPhoneAuthorization -> component.onOutput(
                    AuthorizationProfileComponent.Output.OpenPhoneScreen
                )
            }
        }
    }

    AuthorizationProfileComponent(onEvent = component::onEvent, state)
}

@Composable
fun AuthorizationProfileComponent(
    onEvent: (AuthorizationProfileStore.Intent) -> Unit,
    state: AuthorizationProfileStore.State
) {

    val closeIcon1 = remember { mutableStateOf(false) }
    val borderColor1 = remember { mutableStateOf(textGrayColor) }
    val leadingColor1 = remember { mutableStateOf(textGrayColor) }

    val closeIcon2 = remember { mutableStateOf(false) }
    val borderColor2 = remember { mutableStateOf(textGrayColor) }
    val leadingColor2 = remember { mutableStateOf(textGrayColor) }

    var personName by remember { mutableStateOf(state.name) }
    var personPost by remember { mutableStateOf(state.post) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(EffectiveTheme.colors.background.primary),
    ) {
        IconButton(
            modifier = Modifier.size(size = 48.dp),
            onClick = {
                onEvent(AuthorizationProfileStore.Intent.BackButtonClicked)
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
                text = stringResource(MainRes.strings.input_profile),
                textAlign = TextAlign.Center
            )
            AuthSubTitle(
                modifier = Modifier.padding(bottom = 24.dp),
                text = stringResource(MainRes.strings.select_number),
                textAlign = TextAlign.Center
            )
//            Person name
            UserInfoTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = personName,
                item = UserDataTextFieldType.Person,
                error = state.isErrorName,
                keyboardType = KeyboardType.Text,
                onValueChange = {
                    personName = it
                    onEvent(AuthorizationProfileStore.Intent.NameChanged(name = it))
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

//            POST
            UserInfoTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = personPost,
                item = UserDataTextFieldType.Post,
                error = state.isErrorPost,
                keyboardType = KeyboardType.Text,
                onValueChange = {
                    personPost = it
                    onEvent(AuthorizationProfileStore.Intent.PostChanged(post = it))
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
                        onEvent(AuthorizationProfileStore.Intent.ContinueButtonClicked)
                    }
                )
            }
        }
    }
}
