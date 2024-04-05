package band.effective.office.elevator.ui.booking.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedColors.purple_heart_600
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.TitlePage
import band.effective.office.elevator.ui.booking.models.WorkSpaceUI
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import band.effective.office.elevator.ExtendedColors._66x
import band.effective.office.elevator.ExtendedColors.purple_heart_500
import band.effective.office.elevator.components.LoadingIndicator
import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.domain.models.TypeEndPeriodBooking
import band.effective.office.elevator.ui.models.TypesList
import dev.icerock.moko.resources.StringResource
import kotlinx.datetime.LocalDate

@Composable
fun BookingMainContentScreen(
    workSpaces: List<WorkSpaceUI>,
    scrollState: LazyListState,
    isExpandedCard: Boolean,
    isLoadingWorkspacesList: Boolean,
    isLoadingWorkspaceZones: Boolean,
    isErrorLoadingWorkspaceZones: Boolean,
    isErrorLoadingWorkspacesList: Boolean,
    isExpandedOptions: Boolean,
    iconRotationStateCard: Float,
    iconRotationStateOptions: Float,
    onClickOpenBookAccept: (WorkSpaceUI) -> Unit,
    onClickOpenBookPeriod: () -> Unit,
    onClickOpenChoseZone: () -> Unit,
    onClickExpandedMap: () -> Unit,
    onClickExpandedOption: () -> Unit,
    startDate: LocalDate,
    finishDate: LocalDate,
    bookingPeriod: BookingPeriod,
    typeEndPeriodBooking: TypeEndPeriodBooking,
    repeatBooking: StringResource,
    onClickChangeSelectedType: (TypesList) -> Unit,
    onClickWorkspaceZoneError: () -> Unit,
    onClickWorkspacesListError: () -> Unit,
    selectedTypesList: TypesList
) {
    Scaffold(
        topBar = {
            Box {
                Column(
                    modifier = Modifier.clip(
                        RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
                    ).background(
                        Color.White
                    ).padding(bottom = 24.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.background(Color.White)
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp)
                    ) {
                        TitlePage(
                            title = stringResource(MainRes.strings.booking)
                        )
                        Spacer(modifier = Modifier.weight(.1f))
                        OutlineButtonPurple(
                            onClick = onClickExpandedMap,
                            icon1 = MainRes.images.icon_map,
                            icon2 = MainRes.images.back_button,
                            title = if (isExpandedCard) MainRes.strings.hide_map else MainRes.strings.show_map,
                            rotate = iconRotationStateCard
                        )
                    }
                    OptionMenu(
                        isExpandedCard = isExpandedCard,
                        isExpandedOptions = isExpandedOptions,
                        onClickOpenBookPeriod = onClickOpenBookPeriod,
                        startDate = startDate,
                        finishDate = finishDate,
                        repeatBooking = repeatBooking,
                        onClickChangeSelectedType = onClickChangeSelectedType,
                        selectedTypesList = selectedTypesList,
                        bookingPeriod = bookingPeriod,
                        typeEndPeriodBooking = typeEndPeriodBooking
                    )
                }
                Box(
                    modifier = Modifier.align(Alignment.BottomCenter)
                        .graphicsLayer {
                            translationY = 40f
                        }
                ) {
                    Button(
                        onClick = onClickExpandedOption,
                        shape = CircleShape,
                        border = BorderStroke(
                            width = 1.dp,
                            color = _66x
                        ),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = _66x
                        ),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Image(
                            painter = painterResource(MainRes.images.back_button),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                                .rotate(iconRotationStateOptions),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.background(MaterialTheme.colors.onBackground)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .background(MaterialTheme.colors.onBackground)
                    .padding(horizontal = 16.dp)
                    .height(64.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(MainRes.strings.suitable_options),
                    style = MaterialTheme.typography.subtitle1.copy(
                        color = Color.Black,
                        fontWeight = FontWeight(500)
                    )
                )
                Spacer(modifier = Modifier.weight(.1f))

                if (isErrorLoadingWorkspaceZones) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .width(192.dp)
                            .clickable {
                                onClickWorkspaceZoneError()
                            }
                    ) {
                        Icon(
                            painterResource(MainRes.images.loading_ic),
                            tint = purple_heart_500,
                            contentDescription = null
                        )
                        Text(
                            text = stringResource(MainRes.strings.something_went_wrong),
                            style = MaterialTheme.typography.subtitle1.copy(
                                color = purple_heart_600,
                                fontWeight = FontWeight(400)
                            )
                        )
                    }
                } else if (isLoadingWorkspaceZones) {
                    LinearProgressIndicator(
                        Modifier.width(64.dp),
                        color = purple_heart_600
                    )
                } else {
                    IconButton(
                        onClick = onClickOpenChoseZone, modifier = Modifier.padding(top = 3.dp),
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(MainRes.images.icon_location),
                                tint = purple_heart_500,
                                contentDescription = null
                            )
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = stringResource(MainRes.strings.select_zones),
                                style = MaterialTheme.typography.subtitle1.copy(
                                    color = purple_heart_600,
                                    fontWeight = FontWeight(400)
                                )
                            )
                        }
                    }
                }
            }

            if (isErrorLoadingWorkspacesList) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterHorizontally),
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            onClickWorkspacesListError()
                        }
                ) {
                    Icon(
                        painterResource(MainRes.images.loading_ic),
                        tint = MaterialTheme.colors.primary,
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(MainRes.strings.something_went_wrong),
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Center
                    )
                }
            } else if (isLoadingWorkspacesList) {
                LoadingIndicator()
            } else {
                WorkSpaceList(
                        workSpaces = workSpaces,
                        scrollState = scrollState,
                        onClickOpenBookAccept = onClickOpenBookAccept
                )
            }
        }
    }
}
