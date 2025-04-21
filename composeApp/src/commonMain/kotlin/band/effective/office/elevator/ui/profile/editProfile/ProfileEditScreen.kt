package band.effective.office.elevator.ui.profile.editProfile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.EffectiveTheme
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.LoadingIndicator
import band.effective.office.elevator.components.buttons.PrimaryButton
import band.effective.office.elevator.components.PopupMessage
import band.effective.office.elevator.components.PopupMessageInfo
import band.effective.office.elevator.components.TitlePage
import band.effective.office.elevator.components.generateImageLoader
import band.effective.office.elevator.ui.profile.editProfile.store.ProfileEditStore
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ProfileEditScreen(component: ProfileEditComponent) {
    val state by component.state.collectAsState()

    when (val currentState = state) {
        is ProfileEditStore.State.Loading -> {
            LoadingIndicator()
        }

        is ProfileEditStore.State.Data -> {
            ProfileEditContent(
                state = currentState,
                component = component
            )
        }
    }
}

@Composable
fun ProfileEditContent(state: ProfileEditStore.State.Data, component: ProfileEditComponent) {

    var popupInfo: PopupMessageInfo? by remember { mutableStateOf(null) }

    LaunchedEffect(component) {
        component.label.collect { label ->
            when (label) {
                is ProfileEditStore.Label.ReturnedInProfile -> component.onOutput(
                    ProfileEditComponent.Output.NavigationBack
                )

                is ProfileEditStore.Label.SavedChange -> {
                    popupInfo = PopupMessageInfo(
                        messageResource = MainRes.strings.profile_changes_saved,
                        type = PopupMessageInfo.Type.SUCCESS,
                    )
                }

                is ProfileEditStore.Label.ServerError -> {
                    popupInfo = PopupMessageInfo(
                        messageResource = MainRes.strings.server_error,
                        type = PopupMessageInfo.Type.ERROR,
                    )
                }
            }
        }
    }
    ProfileEditScreenContent(
        imageUrl = state.user.imageUrl,
        telegramError = state.telegramError,
        postError = state.postError,
        nameError = state.nameError,
        phoneError = state.phoneNumberError,
        userName = state.user.userName,
        post = state.user.post,
        telegram = state.user.telegram,
        phoneNumber = state.user.phoneNumber,
        onReturnToProfile = { component.onEvent(ProfileEditStore.Intent.BackInProfileClicked) },
        popupInfo = popupInfo,
        onClosePopup = { popupInfo = null },
        onSaveChange = { userName, post, phoneNumber, telegram ->
            component.onEvent(
                ProfileEditStore.Intent.SaveChangesClicked(
                    userName = userName,
                    post = post,
                    telegram = telegram,
                    phoneNumber = phoneNumber,
                )
            )
        }
    )
}

@Composable
private fun ProfileEditScreenContent(
    modifier: Modifier = Modifier,
    imageUrl: String,
    userName: String,
    post: String,
    telegram: String,
    phoneNumber: String,
    onReturnToProfile: () -> Unit,
    onSaveChange: (userName: String, post: String, phoneNumber: String, telegram: String) -> Unit,
    nameError: StringResource?,
    postError: StringResource?,
    telegramError: StringResource?,
    phoneError: StringResource?,
    popupInfo: PopupMessageInfo?,
    onClosePopup: () -> Unit,
) {
    var userNameText by rememberSaveable { mutableStateOf(userName) }
    var phoneNumberText by rememberSaveable { mutableStateOf(phoneNumber) }
    var postText by rememberSaveable { mutableStateOf(post) }
    var telegramText by rememberSaveable { mutableStateOf(telegram) }
    val imageLoader = remember { generateImageLoader() }
    val request = remember(imageUrl) {
        ImageRequest {
            data(imageUrl)
        }
    }
    val painter = rememberImagePainter(
        request = request,
        imageLoader = imageLoader,
        placeholderPainter = { painterResource(MainRes.images.logo_default) },
        errorPainter = { painterResource(MainRes.images.logo_default) }
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(EffectiveTheme.colors.background.primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            TitlePage(
                title = stringResource(MainRes.strings.profile),
                modifier = Modifier.align(Alignment.Center),
            )
            IconButton(
                onClick = onReturnToProfile,
                modifier = Modifier.align(Alignment.CenterEnd),
            ) {
                Icon(
                    painter = painterResource(MainRes.images.ic_sing_out),
                    contentDescription = stringResource(MainRes.strings.exit),
                    modifier = Modifier.size(18.dp),
                    tint = EffectiveTheme.colors.icon.error,
                )
            }
            if (popupInfo != null) {
                PopupMessage(info = popupInfo, onCloseClick = onClosePopup)
            }
        }
        Spacer(modifier = Modifier.padding(24.dp))
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .border(
                    border = BorderStroke(
                        width = 2.dp,
                        color = EffectiveTheme.colors.stroke.accent,
                    ),
                    shape = CircleShape,
                )
                .padding(2.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally),
        )
        Spacer(modifier = Modifier.height(20.dp))
        EditBlock(
            icon = MainRes.images.ic_user_name,
            iconTitle = stringResource(MainRes.strings.last_name_and_first_name),
            userInfo = userNameText,
            onInfoChange = { userNameText = it },
            errorText = nameError?.let { stringResource(it) },
        )
        Spacer(modifier = Modifier.height(8.dp))
        EditBlock(
            icon = MainRes.images.ic_user_post,
            iconTitle = stringResource(MainRes.strings.post),
            userInfo = postText,
            onInfoChange = { postText = it },
            errorText = postError?.let { stringResource(it) },
        )
        Spacer(modifier = Modifier.height(8.dp))
        EditBlock(
            icon = MainRes.images.ic_telegram,
            iconTitle = stringResource(MainRes.strings.telegram),
            userInfo = telegramText,
            onInfoChange = { telegramText = it },
            errorText = telegramError?.let { stringResource(it) },
        )
        Spacer(modifier = Modifier.height(8.dp))
        EditBlock(
            icon = MainRes.images.ic_phone,
            iconTitle = stringResource(MainRes.strings.phone_number),
            userInfo = phoneNumberText,
            onInfoChange = { phoneNumberText = it },
            errorText = phoneError?.let { stringResource(it) },
        )
        Spacer(modifier = Modifier.height(16.dp))
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onSaveChange(
                    userNameText,
                    postText,
                    phoneNumberText,
                    telegramText
                )
            },
            buttonText = stringResource(MainRes.strings.save),
        )
    }
}
