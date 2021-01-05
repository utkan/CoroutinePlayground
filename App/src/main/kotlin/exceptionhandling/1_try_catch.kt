package exceptionhandling

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.RuntimeException

fun main() {
    val scope = CoroutineScope(Job())
//        scope.launch {
//            try {
//                launch {
//                    functionThatThrowsIt()
//                }
//            } catch (exception: Throwable) {
//                println("Caught: $exception")
//            }
//        }

    try {
        scope.launch {
            launch {
                functionThatThrowsIt()
            }
        }
    } catch (exception: Throwable) {
        println("Caught: $exception")
    }

    Thread.sleep(100)
    print("end of world")
}

fun functionThatThrowsIt() {
    throw RuntimeException()
}