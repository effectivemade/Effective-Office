package band.effective.office.tv.screen.menu.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import band.effective.office.tv.R

@Composable
fun PlayButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {},
    onFocus: () -> Unit = {}
) {
    ButtonAutoplay(
        modifier = modifier,
        onClick = { onClick() },
        onFocus = { onFocus() }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.fillMaxHeight(0.5f),
                painter = painterResource(R.drawable.play_icon),
                contentDescription = null,
                contentScale = ContentScale.FillHeight
            )
            Spacer(modifier = Modifier.padding(2.dp))
            Text(
                text = text,
                color = Color.White,
                modifier = Modifier
                    .alpha(if (it) 1f else 0.5f),
                style = MaterialTheme.typography.body1
            )
        }

    }
}

@Preview()
@Composable
fun PlayButtonPreview() {
    PlayButton(
        Modifier
            .height(60.dp)
            .width(160.dp)
            .clip(shape = RoundedCornerShape(80.dp)),
        text = "play"
    )
}