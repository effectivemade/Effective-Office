package effective.office.modalcustomdialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Dialog(
    modifier: Modifier = Modifier,
    showDialog:Boolean = true,
    onDismissRequest: ()-> Unit,
    content:@Composable ()->Unit,
){
    val interactionSource = remember { MutableInteractionSource() }
    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        visible = showDialog,
        content = {
            Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x4D000000))
                    .clickable(onClick = onDismissRequest,
                indication = null,
                interactionSource = interactionSource)
            ) {
                Column(modifier = modifier.clickable (onClick = {},
                    indication = null,
                    interactionSource = interactionSource))
                    {
                        content()
                }

            }
        }
    )

}