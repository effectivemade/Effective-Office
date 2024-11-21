package band.effective.office.elevator.ui.booking.components.modals

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.EffectiveButton
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun BookingResult(
    onMain: () -> Unit,
    close: () -> Unit,
    modifier: Modifier,
    isLoading: Boolean,
    isError: Boolean
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(size = 16.dp))
            .background(color = ExtendedThemeColors.colors.whiteColor)
            .padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 24.dp)
    ) {
        when {
            isLoading -> CircularProgressIndicator()
            isError -> CreatingDialogError (onClose = close)
            else -> CreatingDialogSuccess(onMain = onMain, onClose = close)
        }
    }
}

@Composable
private fun CreatingDialogSuccess(
    onMain: () -> Unit,
    onClose: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(
                resource = MainRes.strings.place_booked,
            ),
            style = MaterialTheme.typography.h6.merge(
                other = TextStyle(
                    fontWeight = FontWeight(
                        weight = 500
                    ),
                    color = Color.Black
                ),
            ),
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(
                resource = MainRes.strings.good_working_day,
            ),
            style = MaterialTheme.typography.body1.copy(
                color = ExtendedThemeColors.colors._66x
            ),
            textAlign = TextAlign.Center
        )
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        EffectiveButton(
            buttonText = stringResource(resource = MainRes.strings.move_to_main_from_booking),
            onClick = onMain,
            modifier = Modifier.fillMaxWidth()
        )
        EffectiveButton(
            modifier = Modifier.fillMaxWidth(),
            buttonText = stringResource(resource = MainRes.strings.close_booking),
            onClick = onClose,
            buttonColors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = MaterialTheme.colors.primary
            ),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colors.primary
            )
        )
    }
}

@Composable
private fun CreatingDialogError(
    onClose: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(
                resource = MainRes.strings.creating_error,
            ),
            style = MaterialTheme.typography.h6.merge(
                other = TextStyle(
                    fontWeight = FontWeight(
                        weight = 500
                    ),
                    color = Color.Black
                ),
            ),
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(
                resource = MainRes.strings.try_another_place,
            ),
            style = MaterialTheme.typography.body1.copy(
                color = ExtendedThemeColors.colors._66x
            ),
            textAlign = TextAlign.Center
        )
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        EffectiveButton(
            modifier = Modifier.fillMaxWidth(),
            buttonText = stringResource(resource = MainRes.strings.close_booking),
            onClick = onClose,
            buttonColors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = MaterialTheme.colors.primary
            ),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colors.primary
            )
        )
    }
}

