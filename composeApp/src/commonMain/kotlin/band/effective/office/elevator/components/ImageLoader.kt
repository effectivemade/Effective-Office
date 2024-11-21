package band.effective.office.elevator.components

import band.effective.office.elevator.expects.getImageCacheDirectoryPath
import band.effective.office.elevator.expects.setupDefaultComponents
import com.seiko.imageloader.ImageLoader

fun generateImageLoader(
    memCacheSize: Int = 32 * 1024 * 1024, //32MB
    diskCacheSize: Int = 512 * 1024 * 1024 //512MB
) = ImageLoader {
    interceptor {
        memoryCacheConfig {
            maxSizeBytes(memCacheSize)
        }
        diskCacheConfig {
            directory(getImageCacheDirectoryPath())
            maxSizeBytes(diskCacheSize.toLong())
        }
    }
    components {
        setupDefaultComponents()
    }
}
