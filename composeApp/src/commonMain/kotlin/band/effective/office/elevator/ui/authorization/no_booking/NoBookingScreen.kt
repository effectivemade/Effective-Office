package band.effective.office.elevator.ui.authorization.no_booking

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.EffectiveTheme
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.EffectiveGradient
import band.effective.office.elevator.components.buttons.PrimaryButton
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun NoBookingContent(component: NoBookingComponent) {
    NoBookingContent(
        onButtonClick = { component.onOutput(NoBookingComponent.Output.OpenContentScreen) },
    )
}

@Composable
private fun NoBookingContent(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(EffectiveTheme.colors.background.primary),
        contentAlignment = Alignment.Center,
    ) {
        EffectiveGradient()
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = EffectiveTheme.colors.background.tertiary,
                        shape = RoundedCornerShape(32.dp),
                    )
                    .border(
                        width = 1.dp,
                        color = EffectiveTheme.colors.stroke.primary,
                        shape = RoundedCornerShape(32.dp),
                    )
                    .padding(40.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                )
                {
                    Image(
                        painter = painterResource(MainRes.images.ic_effective_triangle),
                        contentDescription = null,
                        modifier = Modifier.size(width = 110.dp, height = 80.dp)
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(
                        text = stringResource(MainRes.strings.no_occupied_tables_title),
                        color = EffectiveTheme.colors.text.primary,
                        style = EffectiveTheme.typography.lMedium,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(MainRes.strings.no_occupied_tables_description),
                        color = EffectiveTheme.colors.text.secondary,
                        style = EffectiveTheme.typography.mMedium,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }


        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            onClick = onButtonClick,
            buttonText = stringResource(MainRes.strings.book_table),
        )
    }
}
