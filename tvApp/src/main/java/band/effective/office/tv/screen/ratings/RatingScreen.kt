package band.effective.office.tv.screen.ratings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun <T> RatingScreen(
    users: List<T>,
    backgroundColor: Color,
    titlePath: Int,
    logoPath: Int,
    ratingTop: @Composable (List<T>) -> Unit
) {
    Box(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 60.dp, end = 120.dp)
        ) {
            TitleRating(
                imagePath = logoPath,
                stringPath = titlePath
            )
            Spacer(modifier = Modifier.height(30.dp))
            ratingTop(users)
        }
    }
}