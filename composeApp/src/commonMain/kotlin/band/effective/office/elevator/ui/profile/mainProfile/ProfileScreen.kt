package band.effective.office.elevator.ui.profile.mainProfile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.EffectiveTheme
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.LoadingIndicator
import band.effective.office.elevator.components.TitlePage
import band.effective.office.elevator.components.UserDetails
import band.effective.office.elevator.components.generateImageLoader
import band.effective.office.elevator.expects.setClipboardText
import band.effective.office.elevator.ui.employee.aboutEmployee.components.EmployeeInfo
import band.effective.office.elevator.ui.profile.mainProfile.store.ProfileStore
import band.effective.office.elevator.utils.prettifyPhoneNumber
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource


@Composable
fun ProfileScreen(component: MainProfileComponent) {
    val user by component.user.collectAsState()

    LaunchedEffect(component) {
        component.label.collect { label ->
            when (label) {
                ProfileStore.Label.OnSignedOut -> component.onOutput(MainProfileComponent.Output.OpenAuthorizationFlow)
            }
        }
    }
    ProfileScreenContent(
        imageUrl = user.user.imageUrl,
        userName = user.user.userName,
        post = user.user.post,
        telegram = user.user.telegram,
        email = user.user.email,
        isLoading = user.isLoading,
        phoneNumber = user.user.phoneNumber,
        onSignOut = { component.onEvent(ProfileStore.Intent.SignOutClicked) },
        onEditProfile = {
            component.onOutput(
                MainProfileComponent.Output.NavigateToEdit(userEdit = user.user.id)
            )
        }
    )
}

@Composable
private fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    imageUrl: String,
    userName: String,
    post: String,
    telegram: String,
    phoneNumber: String,
    email: String?,
    onSignOut: () -> Unit,
    onEditProfile: () -> Unit,
    isLoading: Boolean,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(EffectiveTheme.colors.background.primary)
            .padding(horizontal = 16.dp)
            .padding(top = 40.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Box(
            modifier = modifier.fillMaxWidth()
        ) {
            TitlePage(
                title = stringResource(MainRes.strings.profile),
                modifier = Modifier.align(Alignment.Center)
            )
            IconButton(
                onClick = onSignOut,
                modifier = Modifier.align(Alignment.CenterEnd),
            ) {
                Icon(
                    painter = painterResource(MainRes.images.ic_sing_out),
                    contentDescription = stringResource(MainRes.strings.exit),
                    modifier = Modifier.size(18.dp),
                    tint = EffectiveTheme.colors.icon.error,
                )
            }
        }
        Spacer(modifier = Modifier.padding(24.dp))
        val localUriHandler = LocalUriHandler.current
        when (isLoading) {
            true -> LoadingIndicator()
            false -> {
                UserInfoBlock(
                    imageUrl = imageUrl,
                    userName = userName,
                    userPost = post,
                    telegram = telegram,
                    email = email,
                    phoneNumber = phoneNumber,
                    onOpenUri = { localUriHandler.openUri(it) },
                    onCopyText = { value, label -> setClipboardText(value, label) },
                )
            }
        }
        Spacer(modifier = Modifier.padding(24.dp))
        Text(
            text = "Редактировать профиль",
            style = EffectiveTheme.typography.mMedium,
            color = EffectiveTheme.colors.text.secondary,
            modifier = Modifier.clickable(onClick = { onEditProfile() }),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun UserInfoBlock(
    imageUrl: String,
    userName: String,
    userPost: String,
    telegram: String?,
    email: String?,
    phoneNumber: String?,
    onOpenUri: (uri: String) -> Unit,
    onCopyText: (value: String, label: String) -> Unit,
) {
    Column {
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

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .border(
                    BorderStroke(
                        width = 2.dp,
                        color = EffectiveTheme.colors.stroke.accent,
                    ),
                    shape = CircleShape,
                )
                .padding(2.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally),
        )
        Spacer(modifier = Modifier.padding(12.dp))
        UserDetails(userName = userName, post = userPost)
        Spacer(modifier = Modifier.padding(24.dp))
        if (telegram != null) {
            val iconTitle = stringResource(MainRes.strings.telegram)
            EmployeeInfo(
                icon = MainRes.images.ic_telegram,
                value = telegram,
                iconTitle = iconTitle,
                onClick = { onOpenUri("https://t.me/$telegram") },
                onLongClick = { onCopyText(telegram, iconTitle) },
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (email != null) {
            val iconTitle = stringResource(MainRes.strings.email)
            EmployeeInfo(
                icon = MainRes.images.ic_email,
                value = email,
                iconTitle = iconTitle,
                onClick = { onOpenUri("mailto:$email") },
                onLongClick = { onCopyText(email, iconTitle) },
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (phoneNumber != null) {
            val iconTitle = stringResource(MainRes.strings.phone_number)
            EmployeeInfo(
                icon = MainRes.images.ic_phone,
                value = prettifyPhoneNumber(phoneNumber) ?: phoneNumber,
                iconTitle = iconTitle,
                onClick = { onOpenUri("tel:$phoneNumber") },
                onLongClick = { onCopyText(phoneNumber, iconTitle) },
            )
        }
    }
}
