package band.effective.office.tablet.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.core.MainRes
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette

@Composable
fun CrossButtonView(modifier: Modifier, onDismissRequest:() -> Unit) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterEnd
    ) {
        IconButton(
            onClick = { onDismissRequest() },
            modifier = Modifier
                .size(40.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(MainRes.image.cross),
                contentDescription = "Cross",
                modifier = Modifier.size(25.dp),
                tint = LocalCustomColorsPalette.current.secondaryTextAndIcon
            )
        }
    }
}