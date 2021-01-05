package exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.RuntimeException

fun main() = runBlocking<Unit> {

//    launch {
//        throw RuntimeException()
//    }

//    try {
//        launch {
//            throw RuntimeException()
//        }
//    } catch (throwable: Throwable) {
//        println("Caught $throwable")
//    }

    try {
        doSomethingSuspend()
    } catch (throwable: Throwable) {
        println("Caught $throwable")
    }
}

private suspend fun doSomethingSuspend() {
    coroutineScope {
        launch {
            throw RuntimeException()
        }
    }
}
