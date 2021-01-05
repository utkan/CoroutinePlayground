package exceptionhandling

import kotlinx.coroutines.*

fun main() {
    val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        println("Caught $exception in CoroutineExceptionHandler")
    }

    val scope = CoroutineScope(Job())
//    val scope = CoroutineScope(Job() + exceptionHandler)

//    scope.launch {
//        functionThatThrowsIt()
//    }
//    scope.launch(exceptionHandler) {
//        functionThatThrowsIt()
//    }

    scope.launch() {
        launch(exceptionHandler) { // not handled
            functionThatThrowsIt()
        }
    }
//    functionThatThrowsIt()

//    launch(exceptionHandler) {
//        functionThatThrowsIt()
//    }
    Thread.sleep(500)
    print("end of world")
}