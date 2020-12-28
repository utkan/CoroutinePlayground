package cancellation

import kotlinx.coroutines.*

fun main() = runBlocking {

    val job = launch(Dispatchers.Default) {
        repeat(10) { index ->
//            ensureActive()
//            println("Operation number: $index")
//            Thread.sleep(100)

//            yield()
//            println("Operation number: $index")
//            Thread.sleep(100)

            if (isActive) {
                println("Operation number: $index")
                Thread.sleep(100)
            } else {
//                return@launch
                // perform some cleanup on cancellation
                withContext(NonCancellable) {
                    delay(100)
                    println("Clean up done!")
                }
                throw CancellationException()
            }
        }
    }

    delay(250)
    println("Cancelling Coroutine")
    job.cancel()

    val globalCoroutineJob = GlobalScope.launch {
        repeat(10) {
            println("$it")
            delay(100)
        }
    }
    delay(250)
    globalCoroutineJob.cancel()
    delay(1000)
}