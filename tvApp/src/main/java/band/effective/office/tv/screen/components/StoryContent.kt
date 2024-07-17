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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import band.effective.office.tv.R
import band.effective.office.tv.screen.eventStory.models.AnnualAnniversaryUI
import band.effective.office.tv.screen.eventStory.models.BirthdayUI
import band.effective.office.tv.screen.eventStory.models.EmployeeInfoUI
import band.effective.office.tv.screen.eventStory.models.MonthAnniversaryUI
import band.effective.office.tv.screen.eventStory.models.NewEmployeeUI
import band.effective.office.tv.ui.theme.storyBodyStyle
import band.effective.office.tv.ui.theme.storyEmployeeNameStyle
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
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(employeeInfo.photoUrl).size(Size.ORIGINAL).build()
    )
    if (painter.state is AsyncImagePainter.State.Loading) {
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
                if (employeeInfo.isIntern) {
                    Text(
                        text = stringResource(id = R.string.intern),
                        style = storyBodyStyle,
                    )
                }

                Text(
                    text = employeeInfo.name,
                    style = storyEmployeeNameStyle,
                )
                when (employeeInfo) {
                    is AnnualAnniversaryUI -> {
                        Text(
                            text =
                            stringResource(id = R.string.with_us) + " " + employeeInfo.yearsInCompany + " " + getCorrectDeclension(
                                employeeInfo.yearsInCompany,
                                "год",
                                "года",
                                "лет"
                            ),
                            style = storyBodyStyle,
                        )
                    }

                    is BirthdayUI -> {
                        Text(
                            text = stringResource(id = R.string.congratulations_birthday),
                            style = storyBodyStyle,
                        )
                    }

                    is MonthAnniversaryUI -> {
                        Text(
                            text =
                            stringResource(id = R.string.with_us) + " " + employeeInfo.monthsInCompany + " " + getCorrectDeclension(
                                employeeInfo.monthsInCompany,
                                "месяц",
                                "месяца",
                                "месяцев"
                            ),
                            style = storyBodyStyle,
                        )
                    }

                    is NewEmployeeUI -> {
                        Text(
                            text = stringResource(id = R.string.welcome_to_the_team),
                            style = storyBodyStyle,
                        )
                    }
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
@Preview(widthDp = 960, heightDp = 540)
fun PreviewStoryContent() {
    StoryContent(
        employeeInfo = BirthdayUI("John Doe", "testUrl", true),
        Modifier
            .fillMaxSize()
            .padding(vertical = 64.dp)
    )
}