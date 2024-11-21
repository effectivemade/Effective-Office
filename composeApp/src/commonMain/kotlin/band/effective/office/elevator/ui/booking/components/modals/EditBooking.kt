package band.effective.office.elevator.ui.booking.components.modals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.Repeat
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.EffectiveButton
import band.effective.office.elevator.components.Elevation
import band.effective.office.elevator.domain.models.DayOfWeek
import band.effective.office.elevator.ui.booking.models.Frequency
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun EditBooking(
    place: String,
    startDate: String,
    startTime: String,
    finishDate: String,
    finishTime: String,
    repeatBooking: String,
    switchChecked: Boolean,
    closeClick: () -> Unit,
    onSwitchChange: (Boolean) -> Unit,
    bookStartDate: () -> Unit,
    bookStartTime: () -> Unit,
    bookFinishDate: () -> Unit,
    bookFinishTime: () -> Unit,
    confirmBooking: () -> Unit,
    bookingRepeat: () -> Unit,
    showRepeatDialog: Boolean,
    onClickCloseRepeatDialog: () -> Unit,
    weekDays: List<DayOfWeek>
) {
    Box {

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomEnd = 0.dp,
                        bottomStart = 0.dp
                    )
                )
                .padding(bottom = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(height = 8.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth(fraction = .3f)
                    .height(height = 4.dp)
                    .background(
                        color = ExtendedThemeColors.colors.dividerColor,
                        shape = RoundedCornerShape(size = 16.dp)
                    )
                    .padding(
                        bottom = 8.dp,
                        top = 8.dp
                    )
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                IconButton(
                    onClick = closeClick,
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "close booking")
                }
                Text(
                    text = place,
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight(500))
                )
                Spacer(modifier = Modifier.width(width = 8.dp))
                Icon(imageVector = Icons.Default.Edit, contentDescription = "edit booking")
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth(fraction = 1.0f)
                    .height(height = 1.dp)
                    .background(
                        color = ExtendedThemeColors.colors._66x
                    )
            )

            //Booking area
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(all = 16.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier.size(size = 24.dp),
                                painter = painterResource(MainRes.images.material_calendar_ic),
                                contentDescription = "calendar"
                            )
                            Text(
                                text = stringResource(MainRes.strings.whole_day_booking),
                                style = MaterialTheme.typography.button.merge(
                                    other = TextStyle(
                                        fontWeight = FontWeight(400)
                                    )
                                ),
                                modifier = Modifier.wrapContentSize()
                            )
                        }

                        Switch(
                            checked = switchChecked,
                            onCheckedChange = onSwitchChange,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colors.primary,
                                uncheckedThumbColor = ExtendedThemeColors.colors.switchColor,
                                uncheckedBorderColor = ExtendedThemeColors.colors.transparentColor,
                                checkedBorderColor = ExtendedThemeColors.colors.transparentColor
                            )
                        )
                    }
                    /*
                    //Start booking date
                    TimeLine(
                        date = startDate,
                        time = startTime,
                        elevation = Elevation(),
                        onPickDate = bookStartDate,
                        onPickTime = bookStartTime
                    )

                    //Finish booking date
                    TimeLine(
                        date = finishDate,
                        time = finishTime,
                        elevation = Elevation(),
                        onPickDate = bookFinishDate,
                        onPickTime = bookFinishTime
                    )
                     */

                    //Book period
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = ExtendedThemeColors.colors.transparentColor
                        ),
                        elevation = Elevation(),
                        onClick = bookingRepeat
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                                .padding(vertical = 16.dp)
                        ) {

                            Icon(
                                modifier = Modifier.size(size = 24.dp),
                                imageVector = Icons.Rounded.Repeat,
                                contentDescription = "repeat booking date"
                            )
                            Text(
                                text = repeatBooking,
                                style = MaterialTheme.typography.button.copy(
                                    fontWeight = FontWeight(weight = 400),
                                    color = Color.Black
                                )
                            )
                        }
                    }
                }

                EffectiveButton(
                    buttonText = stringResource(MainRes.strings.confirm_booking),
                    onClick = confirmBooking
                )
            }
        }
        if (showRepeatDialog) {
            BookingRepeatCard(
                onSelected = { onClickCloseRepeatDialog() },
                modifier = Modifier,
                weekDays = weekDays
            )
        }
    }
}