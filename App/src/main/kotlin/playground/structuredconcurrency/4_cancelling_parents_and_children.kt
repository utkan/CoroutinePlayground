package playground.structuredconcurrency

import kotlinx.coroutines.*

fun main() = runBlocking {

    val scope = CoroutineScope(Dispatchers.Default)

    scope.coroutineContext[Job]!!.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Parent Job was cancelled")
        }
    }

    val childCoroutine1Job = scope.launch {
        delay(1000)
        println("Coroutine 1 completed")
    }
    childCoroutine1Job.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Coroutine 1 was cancelled")
        }
    }

    scope.launch {
        delay(1000)
        println("Coroutine 2 completed")
    }.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Coroutine 2 was cancelled")
        }
    }

//    scope.cancel()
//    scope.coroutineContext[Job]!!.join()

//    scope.coroutineContext[Job]!!.cancelAndJoin()

    delay(200)
    childCoroutine1Job.cancelAndJoin()
//    delay(2000)

}