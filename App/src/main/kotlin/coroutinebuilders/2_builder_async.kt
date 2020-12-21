package coroutinebuilders

import kotlinx.coroutines.*

var r=0

fun main() = runBlocking {

    retry(2) {
        println("retried")
        if (r == 0) {
            r++
            throw IllegalAccessError()
        }

        println("ended")
    }

//    Thread.sleep(3000)
//    val startTime = System.currentTimeMillis()
//
//    val deferred1 = getDeferredAsync(this, 1, startTime)
//
//    val deferred2 = getDeferredAsync(this, 2, startTime)
//
//    val resultList = listOf(deferred1.await(), deferred2.await())
//
//    println("Result list: $resultList after ${elapsedMillis(startTime)}ms")
}

// retry with exponential backoff
// inspired by https://stackoverflow.com/questions/46872242/how-to-exponential-backoff-retry-on-kotlin-coroutines
private suspend fun <T> retry(
    times: Int,
    initialDelayMillis: Long = 100,
    maxDelayMillis: Long = 1000,
    factor: Double = 2.0,
    block: suspend () -> T
): T {
    var currentDelay = initialDelayMillis
    repeat(times) {
        try {
            println("block")
            return block()
        } catch (exception: Throwable) {
            exception.printStackTrace()
        }
        delay(currentDelay)
        currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelayMillis)
    }
    return block() // last attempt
}

//fun main() = runBlocking {
//
//    val starTime = System.currentTimeMillis()
//
//    val resultList = mutableListOf<String>()
//    val job1 = getJob(this, 1, resultList, starTime)
//    val job2 = getJob(this, 2, resultList, starTime)
//
//    job1.join()
//    job2.join()
//
//    println("Result list: $resultList after ${elapsedMillis(starTime)}ms")
//}

private fun getJob(
    scope: CoroutineScope,
    number: Int,
    resultList: MutableList<String>,
    starTime: Long
) = scope.launch {
    val result = networkCall(number)
    resultList.add(result)
    println("result$number received $result after ${elapsedMillis(starTime)}ms")
}


private fun getDeferredAsync(scope: CoroutineScope, number: Int, startTime: Long) = scope.async {
    val result = networkCall(number).also {
        println("result received: $it after ${elapsedMillis(startTime)}ms")
    }
    result
}

suspend fun networkCall(number: Int): String {
    delay(500)
    return "Result $number"
}

fun elapsedMillis(startTime: Long) = System.currentTimeMillis() - startTime