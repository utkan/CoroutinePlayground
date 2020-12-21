package coroutinebuilders

import kotlinx.coroutines.*

fun main() = runBlocking {

    val startTime = System.currentTimeMillis()

    val deferred1 = getDeferredAsync(this, 1, startTime)

    val deferred2 = getDeferredAsync(this, 2, startTime)

    val resultList = listOf(deferred1.await(), deferred2.await())

    println("Result list: $resultList after ${elapsedMillis(startTime)}ms")
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