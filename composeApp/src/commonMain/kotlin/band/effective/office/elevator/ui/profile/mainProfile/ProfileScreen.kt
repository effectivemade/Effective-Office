package band.effective.office.elevator.ui.profile.mainProfile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.LoadingIndicator
import band.effective.office.elevator.components.TitlePage
import band.effective.office.elevator.components.generateImageLoader
import band.effective.office.elevator.textGrayColor
import band.effective.office.elevator.ui.models.UserData
import band.effective.office.elevator.ui.models.getAllUserDataProfile
import band.effective.office.elevator.ui.profile.mainProfile.store.ProfileStore
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.rememberAsyncImagePainter
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import io.github.aakira.napier.Napier


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
        isLoading = user.isLoading,
        phoneNumber = user.user.phoneNumber,
        id = user.user.id,
        onSignOut = { component.onEvent(ProfileStore.Intent.SignOutClicked) },
        onEditProfile = { id ->
            component.onOutput(
                MainProfileComponent.Output.NavigateToEdit(
                    userEdit = id
                )
            )
        }
    )
}


@Composable
internal fun ProfileScreenContent(
    imageUrl: String,
    userName: String,
    post: String,
    telegram: String,
    phoneNumber: String,
    onSignOut: () -> Unit,
    onEditProfile: (id: String) -> Unit,
    id: String,
    isLoading: Boolean
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .padding(horizontal = 16.dp).fillMaxWidth().padding(top = 40.dp)
        ) {
            TitlePage(
                stringResource(MainRes.strings.profile)
            )
            Spacer(modifier = Modifier.weight(.1f))
            OutlinedButton(
                onClick = onSignOut,
                shape = RoundedCornerShape(size = 8.dp),
                border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(MainRes.images.exit),
                        contentDescription = null,
                        tint = MaterialTheme.colors.secondary
                    )
                    Text(
                        stringResource(MainRes.strings.exit),
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
        when (isLoading) {
            true -> LoadingIndicator()
            false -> {
                ProfileInfoAboutUser(imageUrl, userName, post, { onEditProfile(id) }, id)

                LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 24.dp))
                {
                    items(getAllUserDataProfile()) { item ->
                        FieldsItemStyle(
                            item = item,
                            { onEditProfile(id) },
                            id = id,
                            telegram = telegram,
                            phoneNumber = phoneNumber
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileInfoAboutUser(
    imageUrl: String,
    userName: String,
    post: String,
    onEditProfile: (id: String) -> Unit,
    id: String
) {
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

    Box {
        Surface(
            modifier = Modifier.size(88.dp).align(Alignment.Center),
            shape = CircleShape,
            color = ExtendedThemeColors.colors.purple_heart_100
        ) {
            Image(
                modifier = Modifier.fillMaxSize().align(Alignment.Center),
                painter = painter,
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
        }
        IconButton(
            onClick = { onEditProfile(id) },
            modifier = Modifier.size(24.dp).align(Alignment.TopEnd)
        ) {
            Image(
                painter = painterResource(MainRes.images.edit_profile_image),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }
    }

    Text(
        userName,
        style = MaterialTheme.typography.subtitle1,
        color = Color.Black,
        modifier = Modifier.padding(top = 12.dp)
    )
    Text(
        post,
        style = MaterialTheme.typography.subtitle1,
        color = textGrayColor,
        modifier = Modifier.padding(top = 8.dp)
    )
}


@Composable
private fun FieldsItemStyle(
    item: UserData,
    onEditProfile: (id: String) -> Unit,
    id: String,
    phoneNumber: String,
    telegram: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .padding(horizontal = 16.dp).fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(item.icon),
            contentDescription = null,
            tint = textGrayColor
        )
        Text(
            stringResource(item.title),
            style = MaterialTheme.typography.subtitle1,
            color = textGrayColor,
            modifier = Modifier.padding(start = 12.dp)
        )
        Spacer(modifier = Modifier.weight(.1f))
        var text = when (item) {
            UserData.Phone -> phoneNumber
            UserData.Telegram -> telegram
        }
        Text(
            text = text,
            style = MaterialTheme.typography.subtitle1,
            color = ExtendedThemeColors.colors.blackColor
        )
        IconButton(onClick = { onEditProfile(id) }) {
            Icon(
                painter = painterResource(MainRes.images.next),
                contentDescription = null,
                tint = ExtendedThemeColors.colors.purple_heart_700
            )
        }
    }
    Divider(color = textGrayColor, thickness = 1.dp)
}
