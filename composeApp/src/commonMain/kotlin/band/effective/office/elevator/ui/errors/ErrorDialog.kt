package band.effective.office.elevator.ui.errors

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import band.effective.office.elevator.EffectiveTheme
import band.effective.office.elevator.components.PrimaryButton
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onClickButton: () -> Unit,
    onClickText: () -> Unit,
    icon: ImageResource,
    errorTitle: StringResource,
    errorSubtitle: StringResource,
    primaryButtonText: StringResource,
    secondaryButtonText: StringResource,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        content = {
            Card(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(
                        EffectiveTheme.colors.background.primary,
                        shape = RoundedCornerShape(28.dp)
                    ),
                shape = RoundedCornerShape(28.dp),
                border = BorderStroke(width = 1.dp, color = EffectiveTheme.colors.stroke.primary)
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 40.dp, horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = EffectiveTheme.colors.graph.violet,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(errorTitle),
                        color = EffectiveTheme.colors.text.primary,
                        style = EffectiveTheme.typography.mMedium,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(errorSubtitle),
                        color = EffectiveTheme.colors.text.secondary,
                        style = EffectiveTheme.typography.sMedium,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    PrimaryButton(
                        onClick = onClickButton,
                        buttonText = stringResource(primaryButtonText),
                    )
                    TextButton(
                        onClick = onClickText,
                        content = {
                            Text(
                                text = stringResource(secondaryButtonText),
                                color = EffectiveTheme.colors.text.accent,
                                style = EffectiveTheme.typography.sMedium,
                                textAlign = TextAlign.Center,
                            )
                        }
                    )
                }
            }
        }
    )
}
