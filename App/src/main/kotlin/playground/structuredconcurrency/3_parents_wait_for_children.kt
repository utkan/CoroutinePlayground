package playground.structuredconcurrency

import kotlinx.coroutines.*


fun main() = runBlocking {

    val scope = CoroutineScope(Dispatchers.Default)

    val parentCoroutineJob = scope.launch {
        launch {
            delay(1000)
            println("Child Coroutine 1 has completed!")
        }
        launch {
            delay(1000)
            println("Child Coroutine 2 has completed!")
        }
    }
    println("join")
    parentCoroutineJob.join()
    println("Parent Coroutine has completed!")
}