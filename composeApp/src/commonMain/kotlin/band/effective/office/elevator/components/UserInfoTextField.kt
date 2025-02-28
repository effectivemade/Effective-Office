package band.effective.office.elevator.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.EffectiveTheme
import band.effective.office.elevator.ui.models.UserDataTextFieldType
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun UserInfoTextField(
    modifier: Modifier = Modifier,
    item: UserDataTextFieldType,
    error: Boolean,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    text: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    var textValue by remember { mutableStateOf(text) }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = textValue,
        onValueChange = {
            textValue = it
            onValueChange(it)
        },
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        textStyle = EffectiveTheme.typography.sMedium,

        colors = OutlinedTextColorsSetup(),
        isError = error,
        visualTransformation = visualTransformation,
        placeholder = {
            Text(
                text = stringResource(item.placeholder),
                color = EffectiveTheme.colors.text.additional,
                style = EffectiveTheme.typography.sMedium,
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Done)
    )
}
