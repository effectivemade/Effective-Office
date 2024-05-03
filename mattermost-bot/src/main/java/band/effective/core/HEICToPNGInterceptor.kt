package band.effective.core

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import okhttp3.MediaType
import java.io.ByteArrayOutputStream

class HEICToPNGInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        val contentType = originalResponse.body()?.contentType()
        val bodyBytes = originalResponse.body()?.bytes()

        if (contentType?.type() == "image/heic" && bodyBytes != null) {
        val bitmap = decodeHEIC(bodyBytes)
        val outputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val pngBytes = outputStream.toByteArray()

        return originalResponse.newBuilder()
            .body(ResponseBody.create(MediaType.get("image/png"), pngBytes))
            .build()
            }
        return originalResponse
    }

    private fun decodeHEIC(bytes: ByteArray?): Bitmap? {
        return try {
            val inputStream = bytes?.inputStream()
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            null
        }
    }
}