package band.effective.office.elevator.ui.profile.editProfile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.EffectiveTheme
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun EditBlock(
    modifier: Modifier = Modifier,
    icon: ImageResource,
    iconTitle: String,
    userInfo: String,
    onInfoChange: (String) -> Unit,
    errorText: String?,
) {
    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = EffectiveTheme.colors.input.lockNormal,
                    shape = RoundedCornerShape(8.dp),
                )
                .border(
                    BorderStroke(
                        width = 1.dp,
                        color = if (errorText == null) {
                            EffectiveTheme.colors.input.lockNormal
                        } else {
                            EffectiveTheme.colors.stroke.error
                        },
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .border(
                            border = BorderStroke(
                                width = 1.dp,
                                color = EffectiveTheme.colors.stroke.primary,
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(
                            color = EffectiveTheme.colors.background.primary,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(4.dp),
                        tint = EffectiveTheme.colors.icon.accent,
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    Text(
                        text = iconTitle,
                        style = EffectiveTheme.typography.xsMedium,
                        color = EffectiveTheme.colors.text.additional,
                    )
                    BasicTextField(
                        value = userInfo,
                        onValueChange = onInfoChange,
                        textStyle = TextStyle.Default.copy(
                            color = EffectiveTheme.colors.text.primary,
                        ),
                    )
                }
            }
        }

        if (errorText != null) {
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = errorText,
                color = EffectiveTheme.colors.text.error,
                style = EffectiveTheme.typography.xsMedium,
                textAlign = TextAlign.Start
            )
        }
    }
}
