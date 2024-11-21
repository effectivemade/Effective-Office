package band.effective.office.elevator.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DropDownMenu(
    expanded: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
){
        AnimatedVisibility(
            enter = fadeIn(),
            modifier = modifier,
            exit = fadeOut(),
            visible = expanded,
            content ={
                content()
            }
        )
}