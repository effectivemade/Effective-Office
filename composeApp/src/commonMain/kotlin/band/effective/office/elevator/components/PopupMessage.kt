package band.effective.office.elevator.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.EffectiveTheme
import band.effective.office.elevator.MainRes
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

data class PopupMessageInfo(
    val messageResource: StringResource,
    val type: Type,
) {

    enum class Type {
        SUCCESS,
        ERROR,
    }
}

@Composable
fun PopupMessage(
    modifier: Modifier = Modifier,
    info: PopupMessageInfo,
    onCloseClick: () -> Unit,
) {
   Row(
       modifier = modifier
           .fillMaxWidth()
           .background(
               color = EffectiveTheme.colors.background.primary,
               shape = RoundedCornerShape(16.dp),
           )
           .border(
               border = BorderStroke(
                   width = 1.dp,
                   color = EffectiveTheme.colors.stroke.primary,
               ),
               shape = RoundedCornerShape(16.dp),
           )
           .padding(vertical = 12.dp, horizontal = 20.dp),
       verticalAlignment = Alignment.CenterVertically,
       horizontalArrangement = Arrangement.spacedBy(8.dp),
   ) {
       if (info.type == PopupMessageInfo.Type.SUCCESS) {
           Icon(
               modifier = Modifier.size(20.dp),
               painter = painterResource(MainRes.images.ic_success),
               tint = EffectiveTheme.colors.icon.success,
               contentDescription = null,
           )
       }
       Text(
           modifier = Modifier.weight(1f),
           text = stringResource(info.messageResource),
           color = EffectiveTheme.colors.text.primary,
           style = EffectiveTheme.typography.mMedium,
           textAlign = TextAlign.Start,
       )
       IconButton(
           onClick = onCloseClick,
       ) {
           Icon(
               modifier = Modifier.size(14.dp),
               painter = painterResource(MainRes.images.ic_close),
               tint = EffectiveTheme.colors.icon.secondary,
               contentDescription = stringResource(MainRes.strings.close)
           )
       }
   }
}
