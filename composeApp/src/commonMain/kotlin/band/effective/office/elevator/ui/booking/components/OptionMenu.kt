package band.effective.office.elevator.ui.booking.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.ZoomableBox
import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.domain.models.TypeEndPeriodBooking
import band.effective.office.elevator.ui.booking.components.modals.noPeriodReserve
import band.effective.office.elevator.ui.booking.models.Frequency
import band.effective.office.elevator.ui.booking.models.WorkSpaceType
import band.effective.office.elevator.ui.models.TypesList
import band.effective.office.elevator.utils.MonthLocalizations
import band.effective.office.elevator.utils.stringFromBookPeriod
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.datetime.LocalDate

@Composable
fun OptionMenu(
    isExpandedCard: Boolean,
    isExpandedOptions: Boolean,
    onClickOpenBookPeriod: () -> Unit,
    startDate: LocalDate,
    finishDate: LocalDate,
    repeatBooking: StringResource,
    bookingPeriod: BookingPeriod,
    typeEndPeriodBooking: TypeEndPeriodBooking,
    onClickChangeSelectedType: (TypesList) -> Unit,
    selectedTypesList: TypesList
) {
    val date = stringFromBookPeriod(
            bookingPeriod = bookingPeriod,
            finishDate = finishDate,
            startDate = startDate,
            typeEndPeriodBooking = typeEndPeriodBooking,
            repeatBooking = repeatBooking
        )


    Column {
        AnimatedVisibility(visible = isExpandedCard) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(.4f)
                    .background(Color.White),
                verticalArrangement = Arrangement.Center
            ) {
                ZoomableBox(minScale = 1f) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer(
                                scaleX = scale,
                                scaleY = scale,
                                translationX = offsetX,
                                translationY = offsetY
                            ),
                        painter = painterResource(MainRes.images.map_zykova),
                        contentDescription = "office map"
                    )
                }
            }
        }

        AnimatedVisibility(visible = isExpandedOptions) {
            Column(modifier = Modifier.padding(top = 16.dp).padding(horizontal = 16.dp)) {
                Text(
                    text = stringResource(MainRes.strings.type_booking),
                    style = MaterialTheme.typography.subtitle1,
                    color = ExtendedThemeColors.colors.blackColor
                )
                Row(
                    modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    val types = listOf(
                        TypesList(
                            name = MainRes.strings.workplace,
                            icon = MainRes.images.table_icon,
                            type = WorkSpaceType.WORK_PLACE
                        ),
                        TypesList(
                            name = MainRes.strings.meeting_room,
                            icon = MainRes.images.icon_meet,
                            type = WorkSpaceType.MEETING_ROOM
                        )
                    )
                    val selectedType = remember { mutableStateOf(selectedTypesList) }

                    types.forEach { type ->
                        val selected = selectedType.value == type
                        Box(
                            modifier = Modifier.padding(
                                end = 12.dp
                            ).border(
                                width = 1.dp,
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colors.secondary
                            ).background(
                                color = if (selected) {
                                    MaterialTheme.colors.background
                                } else {
                                    ExtendedThemeColors.colors.whiteColor
                                }
                            ).selectable(
                                selected = selected,
                                onClick = {
                                    selectedType.value = type
                                    onClickChangeSelectedType(selectedType.value)
                                }
                            )
                        ) {
                            Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 24.dp)) {
                                Icon(
                                    painter = painterResource(type.icon),
                                    contentDescription = null,
                                    tint = MaterialTheme.colors.secondary
                                )
                                Text(
                                    modifier = Modifier.padding(start = 8.dp),
                                    text = stringResource(type.name),
                                    style = MaterialTheme.typography.body2,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = stringResource(MainRes.strings.booking_period),
                    style = MaterialTheme.typography.subtitle1,
                    color = ExtendedThemeColors.colors.blackColor
                )
                Row(
                    modifier = Modifier.padding(top = 8.dp)
                        .clickable(onClick = onClickOpenBookPeriod),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onClickOpenBookPeriod
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(MainRes.images.material_calendar_ic),
                            contentDescription = null,
                            tint = MaterialTheme.colors.secondary
                        )
                    }
                    Text(
                        text = date,
                        modifier = Modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}