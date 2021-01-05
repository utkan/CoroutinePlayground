package exceptionhandling

import kotlinx.coroutines.*
import java.lang.RuntimeException

//fun main() = runBlocking<Unit> {
//    try {
//        coroutineScope {
//            launch {
//                throw RuntimeException()
//            }
//        }
//    } catch (throwable: Throwable) {
//        println("Caught $throwable")
//    }
//}


//fun main() = runBlocking<Unit> {
//    try {
//        supervisorScope {
//            throw RuntimeException()
//        }
//    } catch (throwable: Throwable) {
//        println("Caught $throwable")
//    }
//}


//fun main() = runBlocking<Unit> {
//    try {
//        supervisorScope {
//            launch {
//                throw RuntimeException()
//            }
//        }
//    } catch (throwable: Throwable) {
//        println("Caught $throwable")
//    }
//}


//fun main() = runBlocking<Unit> {
//    try {
//        supervisorScope {
//            async {
//                throw RuntimeException()
//            }
//        }
//    } catch (throwable: Throwable) {
//        println("Caught $throwable")
//    }
//}


//fun main() = runBlocking<Unit> {
//    try {
//        supervisorScope {
//            async {
//                throw RuntimeException()
//            }
//        }.await()
//    } catch (throwable: Throwable) {
//        println("Caught $throwable")
//    }
//}


//fun main() = runBlocking<Unit> {
//    try {
//        supervisorScope {
//            val deferred = async {
//                throw RuntimeException()
//            }
//            launch {
//                deferred.await()
//            }
//        }
//    } catch (throwable: Throwable) {
//        println("Caught $throwable")
//    }
//}


//fun main() = runBlocking<Unit> {
//    try {
//        supervisorScope {
//            launch {
//                throw RuntimeException()
//            }
//        }
//    } catch (throwable: Throwable) {
//        println("Caught $throwable")
//    }
//}

fun main() {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Caught $throwable in CoroutineExceptionHandler")
    }

    val scope = CoroutineScope(Job())

    scope.launch(exceptionHandler) {
        try {
            supervisorScope {
                launch {
                    println("CEH: ${coroutineContext[CoroutineExceptionHandler]}")
                    throw RuntimeException()
                }
            }
        } catch (throwable: Throwable) {
            println("Caught $throwable")
        }
    }

    Thread.sleep(100)
}