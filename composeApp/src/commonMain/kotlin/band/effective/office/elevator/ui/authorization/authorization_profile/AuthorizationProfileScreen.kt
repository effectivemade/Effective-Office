package band.effective.office.elevator.ui.authorization.authorization_profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
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
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.EffectiveButton
import band.effective.office.elevator.components.UserInfoTextField
import band.effective.office.elevator.expects.showToast
import band.effective.office.elevator.textGrayColor
import band.effective.office.elevator.ui.authorization.authorization_profile.store.AuthorizationProfileStore
import band.effective.office.elevator.ui.authorization.components.AuthTabRow
import band.effective.office.elevator.ui.authorization.components.AuthTitle
import band.effective.office.elevator.ui.models.UserDataTextFieldType
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
                onEvent(AuthorizationProfileStore.Intent.BackButtonClicked)
            }) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                tint = Color.Black,
                contentDescription = "image_back"
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        AuthTabRow(1)

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            AuthTitle(
                text = stringResource(MainRes.strings.input_profile),
                modifier = Modifier.padding(bottom = 7.dp),
                textAlign = TextAlign.Start
            )

//            Person name
            UserInfoTextField(
                text = personName,
                item = UserDataTextFieldType.Person,
                error = state.isErrorName,
                keyboardType = KeyboardType.Text,
                onValueChange = {
                    if (it.isNotEmpty()) {
                        closeIcon1.value = true
                        leadingColor1.value = Color.Black
                        borderColor1.value = ExtendedThemeColors.colors.trinidad_400
                    } else {
                        borderColor1.value = textGrayColor
                        closeIcon1.value = false
                        leadingColor1.value = textGrayColor
                    }
                    personName = it
                    onEvent(AuthorizationProfileStore.Intent.NameChanged(name = it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .onFocusChanged {
                        if (it.isFocused) {
                            borderColor1.value = ExtendedThemeColors.colors.trinidad_400
                        } else {
                            borderColor1.value = textGrayColor
                            leadingColor1.value = textGrayColor
                        }
                    }
            )

            Spacer(modifier = Modifier.height(16.dp))

//            POST
            UserInfoTextField(
                text = personPost,
                item = UserDataTextFieldType.Post,
                error = state.isErrorPost,
                keyboardType = KeyboardType.Text,
                onValueChange = {
                    if (it.isNotEmpty()) {
                        closeIcon2.value = true
                        leadingColor2.value = ExtendedThemeColors.colors.blackColor
                        borderColor2.value = ExtendedThemeColors.colors.trinidad_400
                    } else {
                        borderColor2.value = textGrayColor
                        closeIcon2.value = false
                        leadingColor2.value = textGrayColor
                        personPost = it
                    }

                    onEvent(AuthorizationProfileStore.Intent.PostChanged(post = it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .onFocusChanged {
                        if (it.isFocused) {
                            borderColor2.value = ExtendedThemeColors.colors.trinidad_400
                        } else {
                            borderColor2.value = textGrayColor
                            leadingColor2.value = textGrayColor
                        }
                    }
            )

            Spacer(modifier = Modifier.height(16.dp))

            EffectiveButton(
                buttonText = stringResource(MainRes.strings._continue),
                onClick = {
                    onEvent(AuthorizationProfileStore.Intent.ContinueButtonClicked)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
