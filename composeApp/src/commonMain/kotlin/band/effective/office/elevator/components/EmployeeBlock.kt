package band.effective.office.elevator.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.EffectiveTheme
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.ui.employee.aboutEmployee.components.EmployeeInfo
import band.effective.office.elevator.utils.prettifyPhoneNumber
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun EmployeeBlock(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    userName: String?,
    post: String?,
    telegram: String?,
    email: String?,
    phoneNumber: String?,
    onCopyText: (value: String, label: String) -> Unit,
    onOpenUri: (uri: String) -> Unit,
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

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
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
        )
        Spacer(modifier = Modifier.padding(12.dp))
        UserDetails(userName = userName, post = post)
        Spacer(modifier = Modifier.padding(24.dp))
        telegram?.let {
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
        email?.let {
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
        phoneNumber?.let {
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
