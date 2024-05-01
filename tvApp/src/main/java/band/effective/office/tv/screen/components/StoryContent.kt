package band.effective.office.tv.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tv.R
import band.effective.office.tv.domain.model.notion.EventType
import band.effective.office.tv.screen.eventStory.models.AnnualAnniversaryUI
import band.effective.office.tv.screen.eventStory.models.EmployeeInfoUI
import band.effective.office.tv.screen.eventStory.models.MonthAnniversaryUI
import band.effective.office.tv.screen.eventStory.models.NewEmployeeUI
import band.effective.office.tv.ui.theme.drukLCGWideMedium
import band.effective.office.tv.ui.theme.museoCyrl
import band.effective.office.tv.utils.getCorrectDeclension
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun StoryContent(
    employeeInfo: EmployeeInfoUI,
    modifier: Modifier
) {
    val isAnnualAnnualAnniversary = employeeInfo.eventType == EventType.AnnualAnniversary
    val isBirthday = employeeInfo.eventType == EventType.Birthday
    val isMonthAnniversary  = employeeInfo.eventType == EventType.MonthAnniversary

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(employeeInfo.photoUrl).size(Size.ORIGINAL).build()
    )
    if (painter.state is AsyncImagePainter.State.Loading) {
        //onImageLoading()
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .fillMaxSize()
        ) {
            CircularProgressIndicator(
                color = Color.Black,
                strokeWidth = 8.dp,
                modifier = Modifier.size(64.dp)
            )
        }
    } else {
        //onImageLoaded()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxSize()
                .padding(vertical = 64.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .width(500.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = employeeInfo.name,
                    fontSize = 64.sp,
                    fontFamily = museoCyrl,
                    color = Color.Black,
                    fontStyle = FontStyle.Italic
                )
                if (isAnnualAnnualAnniversary) {
                    val story = employeeInfo as AnnualAnniversaryUI
                    Text(
                        text =
                        stringResource(id = R.string.with_us) + " " + story.yearsInCompany + " " + getCorrectDeclension(
                            story.yearsInCompany,
                            "год",
                            "года",
                            "лет"
                        ),
                        fontSize = 54.sp,
                        color = Color.Black,
                        fontFamily = drukLCGWideMedium,
                    )
                } else if (isBirthday) {
                    Text(
                        text = stringResource(id = R.string.congratulations_birthday),
                        fontSize = 54.sp,
                        color = Color.Black,
                        fontFamily = drukLCGWideMedium,
                    )
                } else if (isMonthAnniversary) {
                    val story = employeeInfo as MonthAnniversaryUI
                    Text(
                        text =
                        stringResource(id = R.string.with_us) + " " + story.monthsInCompany + " " + getCorrectDeclension(
                            story.monthsInCompany,
                            "месяц",
                            "месяца",
                            "месяцев"
                        ),
                        fontSize = 54.sp,
                        color = Color.Black,
                        fontFamily = drukLCGWideMedium,
                    )
                }
                else {
                    Text(
                        text = stringResource(id = R.string.welcome_to_the_team),
                        fontSize = 54.sp,
                        color = Color.Black,
                        fontFamily = drukLCGWideMedium,
                    )
                }
            }
            Image(
                painter = painter,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(300.dp)
                    .padding(10.dp)
                    .clip(CircleShape),
                contentDescription = "Employee photo"
            )
        }
    }
}

@Composable
@Preview
fun PreviewStoryContent() {
    StoryContent(
        employeeInfo = NewEmployeeUI("John Doe", "testUrl"),
        Modifier
            .fillMaxSize()
            .padding(vertical = 64.dp)
    )
}