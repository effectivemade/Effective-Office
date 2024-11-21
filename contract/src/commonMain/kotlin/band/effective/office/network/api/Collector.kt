package band.effective.office.network.api

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update

class Collector<T>(defaultValue: T) {
    private data class CollectableElement<T>(val value: T, val number: Long)

    private val collection = MutableStateFlow(CollectableElement(defaultValue, 0))

    fun flow(scope: CoroutineScope) =
        collection.map { it.value }.shareIn(
            scope = scope,
            started = SharingStarted.Lazily,
            replay = 1
        )

    fun emit(value: T) {
        collection.update { CollectableElement(value = value, number = it.number + 1) }
    }
}