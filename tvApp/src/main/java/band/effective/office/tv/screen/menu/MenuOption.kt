package band.effective.office.tv.screen.menu

import band.effective.office.tv.R

enum class MenuOption(val icon: Int, val title: Int) {
    Autoplay(R.drawable.autoplay, R.string.autoplay),
    Photo(R.drawable.photo, R.string.photo),
    Video(R.drawable.video, R.string.video)
}