package band.effective.office.tv.screen.photo.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tv.R
import band.effective.office.tv.screen.load.LoadScreen
import band.effective.office.tv.screen.photo.model.Photo
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import okhttp3.OkHttpClient


@Composable
fun PhotoUIItem(image: Photo, modifier: Modifier = Modifier, okHttpClient: OkHttpClient) {

    val photoWith = LocalConfiguration.current.screenWidthDp.dp
    val photoHeight = LocalConfiguration.current.screenHeightDp.dp

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .okHttpClient(okHttpClient)
        .build()

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier.size(
                width = photoWith,
                height = photoHeight,
            ),
            imageLoader = imageLoader,
            model = image.photoThumb,
            loading = { LoadScreen(stringResource(id = R.string.photo_title)) },
            contentDescription = null,
            contentScale = ContentScale.Fit,
            error = {
                Text(
                    text = stringResource(id = R.string.error_show_photo),
                    fontSize = 40.sp,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize()
                )
            }
        )
    }
}