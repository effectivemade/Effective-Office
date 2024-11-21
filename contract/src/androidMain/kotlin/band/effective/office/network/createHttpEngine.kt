package band.effective.office.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android

actual fun createHttpEngine(): HttpClient = HttpClient(Android)