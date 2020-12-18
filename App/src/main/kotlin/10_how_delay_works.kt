import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking
import java.util.logging.Handler


fun main() = runBlocking {
    println("main starts")
    joinAll(
        async { delayDemonstration(1, 500) },
        async { delayDemonstration(2, 300) }
    )
    println("main ends")
}

suspend fun delayDemonstration(number: Int, delay: Long) {
    println("Coroutine $number starts work")
    delay(delay)
    println("Coroutine $number has finished")
//    android.os.Handler(android.os.Looper.getMainLooper())
//        .postDelayed({
//            println("Coroutine $number has finished")
//        }, 500)
}