package io.github.coroutineusecases

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber

class CoroutineUsecasesOnAndroidApplication : Application() {

//    private val applicationScope = CoroutineScope(SupervisorJob())

//    val androidVersionRepository by lazy {
//        val database = AndroidVersionDatabase.getInstance(applicationContext).androidVersionDao()
//        AndroidVersionRepository(
//            database,
//            applicationScope
//        )
//    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        // Enable Debugging for Kotlin Coroutines in debug builds
        // Prints Coroutine name when logging Thread.currentThread().name
        System.setProperty("kotlinx.coroutines.debug", if (BuildConfig.DEBUG) "on" else "off")
    }
}
