package exceptionhandling

import kotlinx.coroutines.*
import java.lang.RuntimeException

fun main() {

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught $throwable in CoroutineExceptionHandler")
    }

//    val scope = CoroutineScope(Job())
//    scope.launch {
//        delay(200)
//        throw RuntimeException()
//    }

//    val scope = CoroutineScope(Job())
//    scope.async {
//        delay(200)
//        throw RuntimeException()
//    }

//    val scope = CoroutineScope(Job() + exceptionHandler)
//    scope.async {
//        delay(200)
//        throw RuntimeException()
//    }

//    val scope = CoroutineScope(Job() + exceptionHandler)
//    val deferred = scope.async {
//        delay(200)
//        throw RuntimeException()
//    }
//    scope.launch {
//        deferred.await()
//    }

//    val scope = CoroutineScope(Job() + exceptionHandler)
//    scope.launch {
//        async {
//            delay(200)
//            throw RuntimeException()
//        }
//    }

    val scope = CoroutineScope(Job() + exceptionHandler)
    scope.async {
        val deferred = async {
            delay(200)
            throw RuntimeException()
        }
    }
    Thread.sleep(1000)
}