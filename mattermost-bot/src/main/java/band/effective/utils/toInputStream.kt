package band.effective.utils

import java.io.ByteArrayInputStream
import java.io.InputStream

fun ByteArray.toInputStream(): InputStream {
    return ByteArrayInputStream(this)
}