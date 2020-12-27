package playground.structuredconcurrency

import kotlinx.coroutines.*
import java.lang.RuntimeException

fun main() {

    val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        println("Caught exception $exception")
    }

//    val scope = CoroutineScope(SupervisorJob() + exceptionHandler)
    /**
     * Coroutine 1 starts
    Coroutine 2 starts
    Coroutine 1 fails
    Caught exception java.lang.RuntimeException
    Coroutine 2 completed
    Scope gor cancelled: false
     */

    val scope = CoroutineScope(Job() + exceptionHandler)
    /*
    * Coroutine 1 starts
    Coroutine 2 starts
    Coroutine 1 fails
    Coroutine 2 got cancelled!
    Caught exception java.lang.RuntimeException
    Scope got cancelled: true*/
    scope.launch {
        println("Coroutine 1 starts")
        delay(50)
        println("Coroutine 1 fails")
        throw RuntimeException()
    }

    scope.launch {
        println("Coroutine 2 starts")
        delay(500)
        println("Coroutine 2 completed")
    }.invokeOnCompletion { throwable ->
        if (throwable is CancellationException) {
            println("Coroutine 2 got cancelled!")
        }
    }

    Thread.sleep(1000)

    println("Scope got cancelled: ${scope.isActive.not()}")
}