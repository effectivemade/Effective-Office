package band.effective.office.tv.screen.photo.model

import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.domain.model.synology.PhotoDomain

data class Photo(
    val photoThumb: String,
    val filename: String,
)

fun Either.Success<List<PhotoDomain>>.toUIModel() = data.map { it.toUIModel() }

fun PhotoDomain.toUIModel() = Photo(
    photoThumb = photoThumb,
    filename = filename
)