package band.effective.office.tv.screen.photo.model

import band.effective.office.tv.core.network.Either
import band.effective.office.tv.domain.model.synology.PhotoDomain

data class Photo(
    val photoThumb: String
)

fun Either.Success<List<PhotoDomain>>.toUIModel() = data.map { it.toUIModel() }

fun PhotoDomain.toUIModel() = Photo(
    photoThumb = photoThumb
)