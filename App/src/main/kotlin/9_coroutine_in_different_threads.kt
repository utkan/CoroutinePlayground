import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Main starts")
    joinAll(
        async { threadSwitchingCoroutine(1, 500) },
        async { threadSwitchingCoroutine(2, 300) },
        async {
            repeat(5) {
                println("other tasks is working on ${Thread.currentThread().name}")
                delay(100)
            }
        }
    )
    println("Main ends")
}

suspend fun threadSwitchingCoroutine(number: Int, delay: Long) {
    println("Coroutine $number starts work on ${Thread.currentThread().name}")
    delay(delay)
    withContext(Dispatchers.Default) {
        println("Coroutine $number has finished on ${Thread.currentThread().name}")
    }
}