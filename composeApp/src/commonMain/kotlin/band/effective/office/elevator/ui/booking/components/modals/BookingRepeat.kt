package band.effective.office.elevator.ui.booking.components.modals

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.borderGray
import band.effective.office.elevator.components.DropDownMenu
import band.effective.office.elevator.components.buttons.EffectiveButton
import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.domain.models.DayOfWeek
import band.effective.office.elevator.domain.models.TypeEndPeriodBooking
import band.effective.office.elevator.domain.models.listToString
import band.effective.office.elevator.textInBorderGray
import band.effective.office.elevator.utils.MonthLocalizations
import dev.icerock.moko.resources.compose.stringResource
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

//TODO (Artem Gruzdev) refactor this. ALL FILE
@Composable
fun BookingRepeat(
    selectedDayOfEnd: LocalDate,
    backButtonClicked: () -> Unit,
    confirmBooking: (BookingPeriod, TypeEndPeriodBooking) -> Unit,
    onClickOpenCalendar: () -> Unit,
    onSelected: () -> Unit,
) {
    var bookingPeriod: BookingPeriod by remember {
        mutableStateOf(BookingPeriod.Week(weekPeriod = 1, selectedDayOfWeek = emptyList()))
    }

    val periodicity = remember { mutableStateOf("1") }

    val listDaysOfWeek = listOf(
        MainRes.strings.Monday,
        MainRes.strings.Tuesday,
        MainRes.strings.Wednesday,
        MainRes.strings.Thursday,
        MainRes.strings.Friday
    )

    var typeOfEnd: TypeEndPeriodBooking by remember {
        mutableStateOf(TypeEndPeriodBooking.Never)
    }

    val selectedDayOfWeekNumber by remember {
        mutableStateOf(mutableListOf<Int>())
    }

    val endPeriod = remember { mutableStateOf("1") }

    val isExpandedContextMenu = remember { mutableStateOf(false) }
    val localDensity = LocalDensity.current
    var itemWidthDp by remember {
        mutableStateOf(0.dp)
    }
    var itemHeightDp by remember {
        mutableStateOf(0.dp)
    }
    var menuOffset by remember { mutableStateOf(0.dp) }
    var dropDownWidthDp by remember {
        mutableStateOf(50.dp)
    }
    val coroutine = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp
                )
            )
            .height(height = 500.dp)
            .verticalScroll(state = rememberScrollState())
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
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                IconButton(
                    onClick = backButtonClicked,
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back button"
                    )
                }
                Text(
                    text = stringResource(resource = MainRes.strings.booking_repeat),
                    style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight(500)),
                    textAlign = TextAlign.Center
                )
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth(fraction = 1.0f)
                    .height(height = 1.dp)
                    .background(
                        color = ExtendedThemeColors.colors._66x
                    )
            )

            Column(
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    itemWidthDp = with(localDensity) { coordinates.size.width.toDp() }
                    itemHeightDp = with(localDensity) { coordinates.size.height.toDp() }
                }
                    .padding(start = 32.dp)
            ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp, top = 16.dp)
                    ) {
                        Text(
                            text = stringResource(resource = MainRes.strings.booking_repeat_in),
                            style = MaterialTheme.typography.button.copy(fontWeight = FontWeight(400))
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding( end = 16.dp)
                    ) {
                        OutlinedTextField(
                            value = periodicity.value,
                            onValueChange = {
                                periodicity.value=it
                            },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .padding(12.dp)
                                .weight(.3f),
                            singleLine = true,
                            textStyle = MaterialTheme.typography.button.copy(
                                textAlign = TextAlign.Center,
                                color = ExtendedThemeColors.colors.radioTextColor
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            keyboardActions = KeyboardActions.Default
                        )
                        OutlinedTextField(
                            enabled = true,
                            readOnly = true,
                            value =
                            when(bookingPeriod) {
                                is BookingPeriod.Week -> stringResource(MainRes.strings.week)
                                is BookingPeriod.Month -> stringResource(MainRes.strings.month)
                                is BookingPeriod.Year -> stringResource(MainRes.strings.year)
                                else -> stringResource(MainRes.strings.week)
                            },
                            onValueChange = {

                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current)
                            ),
                            modifier = Modifier
                                .padding(
                                    start = 12.dp,
                                    top = 12.dp,
                                    end = 12.dp,
                                    bottom = 12.dp
                                )
                                .weight(.7f),
                            trailingIcon = {
                                IconButton(
                                    onClick =
                                    {
                                        isExpandedContextMenu.value = !isExpandedContextMenu.value
                                    },
                                    modifier = Modifier.pointerInput(true){
                                        detectTapGestures(
                                            onPress = {
                                                coroutine.launch {
                                                    delay(300L)
                                                    menuOffset = (it.y).toDp()
                                                }
                                            }
                                        )
                                    }
                                ) {
                                    Image(
                                        modifier = Modifier,
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "drop down",
                                        contentScale = ContentScale.None
                                    )
                                }
                            }
                        )
                    }
                Box (modifier = Modifier.fillMaxSize(.6f)){
                    Box (modifier = Modifier
                        .fillMaxWidth(0.6f).offset(itemWidthDp - dropDownWidthDp,menuOffset)) {
                        DropDownMenu(
                            expanded = isExpandedContextMenu.value,
                            content = {
                                BookingRepeatContextMenu( onClick = {
                                    bookingPeriod =
                                    when(it){
                                        "Неделя" -> BookingPeriod.Week(weekPeriod = 1, emptyList())
                                        "Месяц" -> BookingPeriod.Month(1)
                                        "Год" -> BookingPeriod.Year(1)
                                        else -> BookingPeriod.NoPeriod
                                    }
                                    isExpandedContextMenu.value=!isExpandedContextMenu.value
                                })
                            },
                            modifier = Modifier.padding(end = 14.dp)
                                .onGloballyPositioned { coordinates ->
                                    dropDownWidthDp = with(localDensity) { coordinates.size.width.toDp() }
                                }
                        )
                    }
                }
                Column {
                    if(bookingPeriod is BookingPeriod.Week) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 16.dp, top = 16.dp)
                        ) {
                            Text(
                                text = stringResource(MainRes.strings.when_repeat),
                                style = MaterialTheme.typography.button.copy(
                                    fontWeight = FontWeight(
                                        weight = 400
                                    )
                                )
                            )
                        }

                        LazyRow(
                            modifier = Modifier.padding(
                                end = 16.dp,
                                top = 16.dp,
                                bottom = 16.dp
                            )
                        ) {
                            itemsIndexed(listDaysOfWeek) { index, days ->
                                var isExpanded = remember { mutableStateOf(false) }
                                Button(
                                    onClick = {
                                        isExpanded.value = !isExpanded.value
                                        selectedDayOfWeekNumber.add(index)
                                    },
                                    contentPadding = PaddingValues(
                                        horizontal = 12.dp,
                                        vertical = 6.dp
                                    ),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = ExtendedThemeColors.colors.whiteColor
                                    ),
                                    modifier = Modifier
                                        .wrapContentWidth().padding(end = 12.dp),
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = if (isExpanded.value)
                                            ExtendedThemeColors.colors.purple_heart_800
                                        else textInBorderGray
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                    elevation = ButtonDefaults.elevation(0.dp, 2.dp, 0.dp)
                                ) {
                                    Row {
                                        Icon(
                                            imageVector = Icons.Rounded.Done,
                                            tint = ExtendedThemeColors.colors.purple_heart_800,
                                            modifier = Modifier.size(
                                                if (isExpanded.value) 20.dp
                                                else 0.dp
                                            )
                                                .align(Alignment.CenterVertically),
                                            contentDescription = "done button"
                                        )
                                    }
                                    Text(
                                        text = stringResource(days),
                                        style = MaterialTheme.typography.body2.copy(
                                            color = if (isExpanded.value) ExtendedThemeColors.colors.purple_heart_800
                                            else textInBorderGray
                                        ),
                                    )
                                }
                            }
                        }
                    }

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth(fraction = 1.0f)
                            .height(height = 1.dp)
                            .background(
                                color = ExtendedThemeColors.colors._66x
                            )
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp, top = 16.dp)
                    ) {
                        Text(
                            text = stringResource(MainRes.strings.book_finish),
                            style = MaterialTheme.typography.button.copy(
                                fontWeight = FontWeight(
                                    weight = 400
                                )
                            ),
                            textAlign = TextAlign.Start
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                typeOfEnd = TypeEndPeriodBooking.Never
                            }
                            .padding(end = 16.dp, top = 8.dp)
                    ) {
                         RadioButton(
                             selected = typeOfEnd is TypeEndPeriodBooking.Never,
                             onClick = {
                                 typeOfEnd = TypeEndPeriodBooking.Never
                             }.also { onSelected() },
                             colors = RadioButtonDefaults.colors(
                                 disabledSelectedColor = MaterialTheme.colors.primary,
                                 disabledUnselectedColor = Color.Black,
                                 selectedColor = MaterialTheme.colors.primary
                             )
                         )

                        Spacer(modifier = Modifier.width(width = 16.dp))

                            Text(
                                text = stringResource(MainRes.strings.never),
                                style = MaterialTheme.typography.button.copy(
                                    color = ExtendedThemeColors.colors.radioTextColor,
                                    fontWeight = FontWeight(400)
                                ),
                                modifier = Modifier.wrapContentHeight()
                            )

                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp, top = 6.dp)
                    ) {
                        RadioButton(
                            selected = typeOfEnd is TypeEndPeriodBooking.DatePeriodEnd,
                            onClick = {
                                typeOfEnd = TypeEndPeriodBooking.DatePeriodEnd(selectedDayOfEnd)
                        }.also { onSelected() },
                            colors = RadioButtonDefaults.colors(
                                disabledSelectedColor = MaterialTheme.colors.primary,
                                disabledUnselectedColor = Color.Black,
                                selectedColor = MaterialTheme.colors.primary
                            )
                        )
                        Spacer(modifier = Modifier.width(width = 16.dp))
                        Text(
                            text = dateConvert(selectedDayOfEnd),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.button.copy(
                                color = ExtendedThemeColors.colors.radioTextColor,
                                fontWeight = FontWeight(400)
                            ),
                            modifier = Modifier
                                .clickable {
                                    typeOfEnd = TypeEndPeriodBooking.DatePeriodEnd(selectedDayOfEnd)
                                    onClickOpenCalendar()
                                }
                                .border(width = 1.dp, color = borderGray, shape = RoundedCornerShape(8.dp))
                                .padding(12.dp),
                        )
                        Spacer(modifier = Modifier.width(width = 48.dp))
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp, top = 8.dp, bottom = 8.dp)
                    ) {
                        RadioButton(
                            selected = typeOfEnd is TypeEndPeriodBooking.CountRepeat,
                            onClick = {
                                typeOfEnd = TypeEndPeriodBooking.CountRepeat(endPeriod.value.ifEmpty { "1" }.toInt())
                        }.also { onSelected() },
                            colors = RadioButtonDefaults.colors(
                                disabledSelectedColor = MaterialTheme.colors.primary,
                                disabledUnselectedColor = Color.Black,
                                selectedColor = MaterialTheme.colors.primary
                            ))
                        Text(
                            text = stringResource(MainRes.strings.booking_1),
                            style = MaterialTheme.typography.button.copy(
                                color = ExtendedThemeColors.colors.radioTextColor,
                                fontWeight = FontWeight(400)
                            ),
                            modifier = Modifier.wrapContentWidth()
                        )
                        Spacer(modifier = Modifier.width(width = 16.dp))

                        OutlinedTextField(
                            value = endPeriod.value,
                            onValueChange = {
                                typeOfEnd = TypeEndPeriodBooking.CountRepeat(it.ifEmpty { "1" }.toInt())
                                endPeriod.value = it
                            },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .padding(12.dp)
                                .weight(weight = 0.1f),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            keyboardActions = KeyboardActions.Default,
                            textStyle = MaterialTheme.typography.button.copy(
                                textAlign = TextAlign.Center,
                                color = ExtendedThemeColors.colors.radioTextColor
                            )
                        )

                        Spacer(modifier = Modifier.width(width = 16.dp))

                        Text(
                            text = stringResource(MainRes.strings.booking_2),
                            style = MaterialTheme.typography.button.copy(
                                color = ExtendedThemeColors.colors.radioTextColor,
                                fontWeight = FontWeight(400)
                            ),
                            modifier = Modifier.wrapContentWidth().wrapContentHeight()
                        )

                        Spacer(modifier = Modifier.width(width = 48.dp))
                    }

                }
            }
        }
        EffectiveButton(
            buttonText = stringResource(MainRes.strings.confirm_booking),
            onClick = {
                confirmBookPeriodChanges(
                    bookingPeriod = bookingPeriod,
                    typeEndPeriodBooking = typeOfEnd,
                    selectedDayOfEnd = selectedDayOfEnd,
                    selectedDayOfWeekNumber = selectedDayOfWeekNumber,
                    periodLength = periodicity.value.toInt(),
                    countRepeat = endPeriod.value.toInt(),
                    confirmBooking = confirmBooking
                )
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
        )
    }
}

private fun confirmBookPeriodChanges(
    selectedDayOfEnd: LocalDate,
    countRepeat: Int,
    bookingPeriod: BookingPeriod,
    periodLength: Int,
    typeEndPeriodBooking: TypeEndPeriodBooking,
    selectedDayOfWeekNumber: List<Int>,
    confirmBooking: (BookingPeriod, TypeEndPeriodBooking) -> Unit
) {
    val typeOfEnd =
        when(typeEndPeriodBooking) {
            is TypeEndPeriodBooking.DatePeriodEnd ->
                TypeEndPeriodBooking.DatePeriodEnd(selectedDayOfEnd)
            is TypeEndPeriodBooking.CountRepeat ->
                 TypeEndPeriodBooking.CountRepeat(countRepeat)
            else -> typeEndPeriodBooking
        }

    val resultBookingPeriod =
        when(bookingPeriod) {
            is BookingPeriod.Week -> {
                val dayOfWeek = getDays(selectedDayOfWeekNumber)
                Napier.d {
                    "selected day of week ${dayOfWeek.listToString()}"
                }
                BookingPeriod.Week(
                    weekPeriod = periodLength,
                    selectedDayOfWeek = dayOfWeek
                )
            }
            is BookingPeriod.Month -> BookingPeriod.Month(periodLength)
            is BookingPeriod.Year -> BookingPeriod.Year(periodLength)
            else -> bookingPeriod
        }

    confirmBooking(resultBookingPeriod, typeOfEnd)
}
private fun dateConvert(date: LocalDate) =
    "${date.dayOfMonth} " +
            "${ MonthLocalizations.getMonthName(date.month, Locale("ru"))
                .substring(0, 3)}." +
            "${date.year}г."


// TODO: replace this in feature. It`s shit code
private fun getDays(dayOfWeek: List<Int>): List<DayOfWeek> {
    val list = listOf(
        DayOfWeek.Monday,
        DayOfWeek.Tuesday,
        DayOfWeek.Wednesday,
        DayOfWeek.Thursday,
        DayOfWeek.Friday,
        DayOfWeek.Saturday
    )

    return dayOfWeek.map { list[it] }
}
