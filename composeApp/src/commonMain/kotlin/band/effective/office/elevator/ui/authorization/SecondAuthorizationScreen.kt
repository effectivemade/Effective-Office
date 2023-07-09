package band.effective.office.elevator.ui.authorization

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.components.OutlinedTextInput
import band.effective.office.elevator.components.PrimaryButton
import band.effective.office.elevator.components.auth_components.AuthSubTitle
import band.effective.office.elevator.components.auth_components.AuthTabRow
import band.effective.office.elevator.components.auth_components.AuthTitle

@Composable
fun SecondAuthScreen(modifier: Modifier) {
    val tabIndex = remember { mutableStateOf(0) }
    val elevation = ButtonDefaults.elevation(
        defaultElevation = 0.dp,
        pressedElevation = 0.dp,
        disabledElevation = 0.dp,
        hoveredElevation = 0.dp,
        focusedElevation = 0.dp
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(all = 16.dp).then(modifier),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(modifier = Modifier.padding(all = 16.dp), onClick = {

        }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "image_back")
        }

        AuthTabRow(tabIndex.value)

        Column(modifier = Modifier.fillMaxSize()) {
            when (tabIndex.value) {
                0 -> Phone()
                1 -> Employee()
                2 -> TG()
            }
        }

        if (tabIndex.value == 2) {
            PrimaryButton(
                text = "Пропустить",
                modifier = Modifier,
                cornerValue = 40.dp,
                contentTextSize = 16.sp,
                paddingValues = PaddingValues(),
                elevation = elevation,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.background
                ),
                border = BorderStroke(width = 2.dp, color = MaterialTheme.colors.secondary),
                onButtonClick = {

                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(
            text = "Продолжить",
            modifier = Modifier,
            cornerValue = 40.dp,
            contentTextSize = 16.sp,
            paddingValues = PaddingValues(),
            elevation = elevation,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary
            ),
            border = null,
            onButtonClick = {
                tabIndex.value++
            }
        )
    }
}

@Composable
fun Phone() {
    val error1 = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        AuthTitle(
            text = "Введите номер",
            modifier = Modifier.padding(bottom = 7.dp),
            textAlign = TextAlign.Start
        )
        AuthSubTitle(
            text = "Укажите номер, по которому коллеги смогли бы перевести вам деньги по СБП",
            modifier = Modifier.padding(bottom = 24.dp),
            textAlign = TextAlign.Start
        )
        OutlinedTextInput(
            hint = "Номер телефона",
            error = error1.value,
            modifier = Modifier,
            leadingHolder = {
                Text(text = "+7")
            },
            onTextChange = {

            })
    }
}

@Composable
fun Employee() {
    val error1 = remember { mutableStateOf(false) }
    val error2 = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        AuthTitle(
            text = "Заполните профиль",
            modifier = Modifier.padding(bottom = 7.dp),
            textAlign = TextAlign.Start
        )
        AuthSubTitle(
            text = "Это необходимо для того, чтобы сотрудники смогли вас узнать",
            modifier = Modifier.padding(bottom = 24.dp),
            textAlign = TextAlign.Start
        )
        OutlinedTextInput(
            hint = "Фамилия Имя",
            error = error1.value,
            modifier = Modifier,
            leadingHolder = {
                Icon(imageVector = Icons.Default.Person, contentDescription = "person")
            },
            onTextChange = {

            })
        OutlinedTextInput(
            hint = "Должность",
            error = error2.value,
            modifier = Modifier,
            leadingHolder = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "bag")
            },
            onTextChange = {

            })
    }
}

@Composable
fun TG() {
    val error1 = remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        AuthTitle(
            text = "Укажите контакты",
            modifier = Modifier.padding(bottom = 7.dp),
            textAlign = TextAlign.Start
        )
        AuthSubTitle(
            text = "Это необходимо для того, чтобы коллегам было удобно связаться с вами",
            modifier = Modifier.padding(bottom = 24.dp),
            textAlign = TextAlign.Start
        )
        OutlinedTextInput(
            hint = "Никнейм в Telegram",
            error = error1.value,
            modifier = Modifier,
            leadingHolder = {
                Icon(imageVector = Icons.Default.Send, contentDescription = "send")
            },
            onTextChange = {

            })
    }
}