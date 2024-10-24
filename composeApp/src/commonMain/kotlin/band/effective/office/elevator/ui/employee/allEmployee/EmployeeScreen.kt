package band.effective.office.elevator.ui.employee.allEmployee

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.LoadingIndicator
import band.effective.office.elevator.components.generateImageLoader
import band.effective.office.elevator.textInBorderGray
import band.effective.office.elevator.theme_light_onPrimary
import band.effective.office.elevator.ui.employee.allEmployee.models.mappers.EmployeeCard
import band.effective.office.elevator.ui.employee.allEmployee.store.EmployeeStore
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.rememberImagePainter
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun EmployeeScreen(component: EmployeeComponent) {

    val employState by component.employState.collectAsState()
    val employeesData = employState.changeShowedEmployeeCards
    val userMessageState = employState.query


    LaunchedEffect(component) {
        component.employLabel.collect { label ->
            when (label) {
                is EmployeeStore.Label.ShowProfileScreen -> component.onOutput(
                    EmployeeComponent.Output.OpenProfileScreen(
                        label.employee
                    )
                )
            }
        }
    }
    EmployeeScreenContent(
        isLoading = employState.isLoading,
        employeesData = employeesData,
        userMessageState = userMessageState,
        onCardClick = { component.onEvent(EmployeeStore.Intent.OnClickOnEmployee(it)) },
        onTextFieldUpdate = { component.onEvent(EmployeeStore.Intent.OnTextFieldUpdate(it)) })
}

@Composable
fun EmployeeScreenContent(
    modifier: Modifier = Modifier,
    employeesData: List<EmployeeCard>,
    userMessageState: String,
    onCardClick: (String) -> Unit,
    onTextFieldUpdate: (String) -> Unit,
    isLoading: Boolean
) {
    //TODO(Artem Gruzdev) need to fix this. We should to use snapshotFlow or derivedStateOf for showing
    // query result
    // see there: https://medium.com/androiddevelopers/effective-state-management-for-textfield-in-compose-d6e5b070fbe5
    var query by remember { mutableStateOf(userMessageState) }

    Column {
        Column(
            modifier = modifier
                .background(theme_light_onPrimary)
                .padding(bottom = 15.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(MainRes.strings.employees),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight(600),//?
                color = ExtendedThemeColors.colors.blackColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 55.dp, end = 15.dp, bottom = 25.dp)
            )
            SearchTextField(
                query = query,
                onTextFieldUpdate = {
                    query = it
                    onTextFieldUpdate(it)
                }
            )
        }
        Column(
            modifier = Modifier
                .background(theme_light_onPrimary)
                .fillMaxSize()
                .padding(start = 20.dp, top = 25.dp, end = 20.dp)
        ) {
            when (isLoading) {
                true -> {
                    LoadingIndicator()
                }

                false -> {
                    LazyColumn {
                        items(employeesData) { employee_Data ->
                            EveryEmployeeCard(emp = employee_Data, onCardClick)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchTextField(
    modifier: Modifier = Modifier,
    query: String,
    onTextFieldUpdate: (String) -> Unit,
) {
    val interactionSource = MutableInteractionSource()
    val isFocused by interactionSource.collectIsFocusedAsState()

    Row(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .border(1.dp, color = Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .padding(vertical = 4.dp)
            .padding(start = 24.dp, end = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(MainRes.images.ic_search),
            tint = if (isFocused) ExtendedThemeColors.colors.trinidad_400 else Color.LightGray,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
        BasicTextField(
            modifier = Modifier
                .padding(16.dp)
                .weight(1f),
            value = query,
            onValueChange = onTextFieldUpdate,
            interactionSource = interactionSource,
            textStyle = TextStyle.Default.copy(fontSize = 14.sp),
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.wrapContentSize(align = Alignment.CenterStart),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    val hint = if (query.isEmpty()) {
                        stringResource(MainRes.strings.search_employee)
                    } else {
                        ""
                    }
                    Text(
                        text = hint,
                        fontSize = 14.sp,
                    )
                    innerTextField()
                }
            }
        )
        if (query.isNotEmpty()) {
            IconButton(
                onClick = { onTextFieldUpdate("") },
            ) {
                Icon(
                    painter = painterResource(MainRes.images.ic_cross),
                    tint = Color.LightGray,
                    contentDescription = stringResource(MainRes.strings.clear_text_field),
                    modifier = Modifier.size(24.dp),
                )
            }
        }
    }
}

@Composable
private fun EveryEmployeeCard(emp: EmployeeCard, onCardClick: (String) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    if (isExpanded) {
        onCardClick(emp.id)
    }

    val imageLoader = generateImageLoader()

    Surface(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 15.dp)
            .animateContentSize()
            .clickable { isExpanded = !isExpanded },
        color = MaterialTheme.colors.onPrimary,
    ) {
        Row(modifier = Modifier.padding(6.dp, 15.dp)) {
            emp.logoUrl.let { url ->
                val request = remember(url) {
                    ImageRequest {
                        data(url)
                    }
                }
                val painter = rememberImagePainter(
                    request = request,
                    imageLoader = imageLoader,
                    placeholderPainter = { painterResource(MainRes.images.logo_default) },
                    errorPainter = { painterResource(MainRes.images.logo_default) }
                )
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(56.dp)
                )
            }

            Column(modifier = Modifier.padding(horizontal = 15.dp)) {
                Text(
                    text = emp.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = ExtendedThemeColors.colors.blackColor,
                )
                Spacer(modifier = Modifier.padding(0.dp, 4.dp))
                Text(
                    text = emp.post,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400),
                    color = textInBorderGray,
                )
            }
        }
    }
}
