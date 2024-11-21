package band.effective.office.tablet.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.core.MainRes
import band.effective.office.tablet.ui.buttons.success.SuccessButton
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.h8

@Composable
fun ErrorMainScreen(resetRequest: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(MainRes.image.disconnect), contentDescription = null)
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = MainRes.string.disconnect, style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = MainRes.string.reset,
            style = MaterialTheme.typography.h6,
            color = LocalCustomColorsPalette.current.secondaryTextAndIcon
        )
        Spacer(modifier = Modifier.height(60.dp))
        SuccessButton(
            modifier = Modifier.height(60.dp).fillMaxWidth(0.3f),
            onClick = { resetRequest() }) {
            Text(text = MainRes.string.reset_button, style = MaterialTheme.typography.h8)
        }
    }
}