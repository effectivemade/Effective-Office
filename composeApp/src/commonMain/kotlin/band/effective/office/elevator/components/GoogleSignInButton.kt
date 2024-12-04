package band.effective.office.elevator.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.MainRes
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
internal fun GoogleSignInButton(modifier: Modifier, onClick: () -> Unit) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(40.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = MaterialTheme.colors.secondary
        ),
        elevation = Elevation()
    ) {
        Image(
            painterResource(MainRes.images.google_icons),
            modifier = Modifier.size(32.dp),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier.padding(6.dp),
            text = stringResource(MainRes.strings.sign_in_google),
            style = MaterialTheme.typography.button
        )
    }
}
