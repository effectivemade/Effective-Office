package band.effective.office.tv.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tv.screen.sport.SportScreen
import band.effective.office.tv.screen.duolingo.DuolingoScreen
import band.effective.office.tv.screen.eventStory.models.DuolingoUserInfo
import band.effective.office.tv.screen.eventStory.models.EmployeeInfoUI
import band.effective.office.tv.screen.eventStory.models.MessageInfo
import band.effective.office.tv.screen.eventStory.models.SportUserInfo
import band.effective.office.tv.screen.eventStory.models.StoryModel
import band.effective.office.tv.screen.eventStory.models.StoryType
import band.effective.office.tv.screen.message.component.OneMessageScreen
import band.effective.office.tv.ui.theme.EffectiveColor
import coil.ImageLoader

@Composable
fun EventStoryScreenContent(
    eventsInfo: List<StoryModel>,
    currentStoryIndex: Int,
    storyProgress: Float = 1f,
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader,
    onImageLoading: () -> Unit,
    onImageLoaded: () -> Unit,
) {
    Surface(
        modifier = modifier,
        color = when(eventsInfo[currentStoryIndex].storyType) {
            StoryType.Sport -> EffectiveColor.backgroundSportColor
            else -> EffectiveColor.white
        }
    ) {
        Column {
            StoryIndicator(
                countStories = eventsInfo.size,
                currentStoryIndex = currentStoryIndex,
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth()
                    .height(8.dp),
                progress = storyProgress
            )
            when (eventsInfo[currentStoryIndex].storyType) {
                StoryType.Employee -> {
                    val storyData = eventsInfo[currentStoryIndex]
                    StoryContent(
                        employeeInfo = storyData as EmployeeInfoUI,
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 64.dp)
                    )
                }
                StoryType.Duolingo -> {
                    val duolingoItem = eventsInfo[currentStoryIndex] as DuolingoUserInfo
                    DuolingoScreen(
                        keySort = duolingoItem.keySort,
                        duolingoUser = duolingoItem.users.take(10)
                    )
                }
                StoryType.Message -> {
                    val messageItem = eventsInfo[currentStoryIndex] as MessageInfo
                    OneMessageScreen(
                        modifier = modifier,
                        imageLoader = imageLoader,
                        message = messageItem.message
                    )
                }

                StoryType.Sport -> {
                    val sportItem = eventsInfo[currentStoryIndex]
                    SportScreen(
                        sportUsers = if (sportItem is SportUserInfo) sportItem.users else emptyList()
                    )
                }
            }

        }
    }
}
