package playground.structuredconcurrency

import kotlinx.coroutines.*

fun main() {
    val scope = CoroutineScope(Job())

    scope.launch {
        println("this: $this")
        doSomeTask()

        launch {
            println("Starting task 3")
            println("this: $this")
            delay(300)
            println("Task 3 completed")
        }
    }

    Thread.sleep(1000)
}

suspend fun  doSomeTask() = coroutineScope {
    println("this: $this")

    launch {
        println("Starting task 1")
        println("this: $this")
        delay(100)
        println("Task 1 completed")
    }

    launch {
        println("Starting task 2")
        println("this: $this")
        delay(200)
        println("Task 2 completed")
    }
}