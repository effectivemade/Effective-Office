package band.effective.office.elevator.utils

/**
 * Stack as type alias of Mutable List
 */
typealias Stack<T> = MutableList<T>

/**
 * Pushes item to [Stack]
 * @param item Item to be pushed
 */
inline fun <T> Stack<T>.push(item: T) = add(item)

/**
 * Pops (removes and return) last item from [Stack]
 * @return item Last item if [Stack] is not empty, null otherwise
 */
fun <T> Stack<T>.pop(): T? = if (isNotEmpty()) removeAt(lastIndex) else null

/**
 * Peeks (return) last item from [Stack]
 * @return item Last item if [Stack] is not empty, null otherwise
 */
fun <T> Stack<T>.peek(): T? = if (isNotEmpty()) this[lastIndex] else null

fun <T> stackOf(vararg elements: T): Stack<T> = mutableListOf(*elements)

fun <T> stackOf():Stack<T> = mutableListOf<T>()