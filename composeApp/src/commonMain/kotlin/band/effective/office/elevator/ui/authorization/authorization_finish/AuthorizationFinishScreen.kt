package band.effective.office.elevator.ui.authorization.authorization_finish

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.EffectiveTheme
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.EffectiveGradient
import band.effective.office.elevator.components.buttons.PrimaryButton
import band.effective.office.elevator.components.generateImageLoader
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun AuthorizationFinishScreen(component: AuthorizationFinishComponent) {
    val state by component.user.collectAsState()

    AuthorizationFinishContent(
        modifier = Modifier
            .fillMaxSize(),
        name = state.name,
        post = state.post,
        avatarUrl = state.avatarUrl,
        onButtonClick = { component.onOutput(AuthorizationFinishComponent.Output.OpenNoBookingScreen) }
    )
}

@Composable
private fun AuthorizationFinishContent(
    modifier: Modifier = Modifier,
    name: String,
    post: String,
    avatarUrl: String?,
    onButtonClick: () -> Unit,
) {
    val imageLoader = remember { generateImageLoader() }
    val painter = avatarUrl?.let { url ->
        rememberImagePainter(
            request = remember(url) {
                ImageRequest {
                    data(url)
                }
            },
            imageLoader = imageLoader,
            placeholderPainter = { painterResource(MainRes.images.logo_default) },
            errorPainter = { painterResource(MainRes.images.logo_default) }
        )
    }
    EffectiveGradient()
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        EffectiveGradient()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(MainRes.strings.finish_registration_title),
                color = EffectiveTheme.colors.text.accent,
                style = EffectiveTheme.typography.mMedium,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(MainRes.strings.finish_registration_description),
                color = EffectiveTheme.colors.text.secondary,
                style = EffectiveTheme.typography.sMedium,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.weight(0.5f))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = EffectiveTheme.colors.background.tertiary,
                        shape = RoundedCornerShape(32.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = EffectiveTheme.colors.stroke.primary,
                        shape = RoundedCornerShape(32.dp),
                    )
                    .padding(40.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Image(
                        painter = painter ?: painterResource(MainRes.images.logo_default),
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
                            .clip(CircleShape),
                    )
                    Spacer(modifier = Modifier.padding(16.dp))
                    Text(
                        text = name,
                        color = EffectiveTheme.colors.text.primary,
                        style = EffectiveTheme.typography.mMedium,
                    )
                    Text(
                        text = post,
                        color = EffectiveTheme.colors.text.secondary,
                        style = EffectiveTheme.typography.sMedium,
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            PrimaryButton(
                onClick = onButtonClick,
                buttonText = stringResource(MainRes.strings.go_book),
            )
        }
    }
}
