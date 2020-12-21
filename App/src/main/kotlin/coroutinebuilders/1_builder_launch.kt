import kotlinx.coroutines.*

//fun main() {
//    GlobalScope.launch {
//        delay(500)
//        println("printed from within Coroutine")
//    }
//    Thread.sleep(1000)
//    println("main ends")
//}
fun main()= runBlocking {
    val job = launch(start = CoroutineStart.LAZY) {
        val result = networkRequest()
        println(result)
        println("result received")
    }
//    delay(200)
    job.join()
//    job.start()
    println("end of runBlocking")
}

suspend fun networkRequest(): String {
    delay(500)
    return "Result"
}