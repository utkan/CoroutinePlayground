package playground.structuredconcurrency

import kotlinx.coroutines.*

val scope = CoroutineScope(Dispatchers.Default)

fun main() = runBlocking {
    val job = scope.launch {
        delay(100)
        println("Coroutine completed")
    }

    job.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Coroutine was cancelled")
        }
    }
    delay(50)
    onDestroy()
}

fun onDestroy() {
    println("Life-time of scope ends")
    scope.cancel()
}

