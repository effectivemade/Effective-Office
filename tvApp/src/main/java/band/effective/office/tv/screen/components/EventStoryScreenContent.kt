package band.effective.office.tv.screen.components

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import band.effective.office.tv.R
import band.effective.office.tv.screen.duolingo.DuolingoScreen
import band.effective.office.tv.screen.eventStory.models.DuolingoUserInfo
import band.effective.office.tv.screen.eventStory.models.EmployeeInfoUI
import band.effective.office.tv.screen.eventStory.models.MessageInfo
import band.effective.office.tv.screen.eventStory.models.SportUserInfo
import band.effective.office.tv.screen.eventStory.models.StoryModel
import band.effective.office.tv.screen.eventStory.models.StoryType
import band.effective.office.tv.screen.eventStory.models.SupernovaUserInfo
import band.effective.office.tv.screen.message.component.OneMessageScreen
import band.effective.office.tv.screen.ratings.RatingScreen
import band.effective.office.tv.screen.ratings.TopRating
import band.effective.office.tv.screen.ratings.sport.SportItem
import band.effective.office.tv.screen.ratings.supernova.SupernovaItem
import band.effective.office.tv.ui.theme.EffectiveColor
import coil.ImageLoader
import coil.decode.DecodeResult
import coil.decode.Decoder
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest

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
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val paths: MutableList<String> = mutableListOf()
        eventsInfo.forEach { event ->
            when (event) {
                is DuolingoUserInfo -> {
                    event.users.forEach {
                        paths.add(it.photo)
                    }
                }
                is SportUserInfo -> {
                    event.users.forEach {
                        paths.add(it.photo)
                    }
                }
                is SupernovaUserInfo -> {
                    event.users.forEach {
                        paths.add(it.photoUrl)
                    }
                }
            }
        }
        paths.forEach {
            val request = ImageRequest.Builder(context)
                .data(it)
                .memoryCachePolicy(CachePolicy.DISABLED)
                .decoderFactory { _, _, _ ->
                    Decoder { DecodeResult(ColorDrawable(Color.BLACK), false) }
                }
                .build()
            context.imageLoader.enqueue(request)
        }
    }
    Surface(
        modifier = modifier,
        color = when(eventsInfo[currentStoryIndex].storyType) {
            StoryType.Duolingo -> EffectiveColor.duolingo
            StoryType.Sport -> EffectiveColor.sport
            StoryType.Supernova -> EffectiveColor.supernova
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
                        duolingoUser = duolingoItem.users
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
                    val sportItems = eventsInfo[currentStoryIndex].let {
                        if (it is SportUserInfo) it.users
                        else emptyList()
                    }
                    RatingScreen(
                        users = sportItems,
                        backgroundColor = EffectiveColor.sport,
                        titlePath = R.string.sport_title,
                        logoPath = R.drawable.sport_logo
                    ) {
                        TopRating(users = it) { modifier, user, _ ->
                            SportItem(
                                modifier = modifier,
                                user = user
                            )
                        }
                    }
                }
                StoryType.Supernova -> {
                    val supernovaItems = eventsInfo[currentStoryIndex].let {
                        if (it is SupernovaUserInfo ) it.users
                        else emptyList()
                    }
                    RatingScreen(
                        users = supernovaItems,
                        backgroundColor = EffectiveColor.supernova,
                        titlePath = R.string.supernova_title,
                        logoPath = R.drawable.supernova
                    ) {
                        TopRating(users = it) { modifier, user, _ ->
                            SupernovaItem(
                                modifier = modifier,
                                user = user
                            )
                        }
                    }
                }
            }
        }
    }
}
