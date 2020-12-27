package io.github.coroutineusecases.usecases.coroutines.usecase14

import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import io.github.coroutineusecases.mock.MockApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class AndroidVersionRepository(
    private var database: AndroidVersionDao,
    private val scope: CoroutineScope,
    private val api: MockApi = mockApi()
) {

    suspend fun getLocalAndroidVersions(): List<AndroidVersion> {
        return database.getAndroidVersions().mapToUiModelList()
    }

    suspend fun loadAndStoreRemoteAndroidVersions(): List<AndroidVersion> {
        return withContext(scope.coroutineContext) {
            val recentVersions = api.getRecentAndroidVersions()
            Timber.d("Recent Android versions loaded")
            for (recentVersion in recentVersions) {
                Timber.d("Insert $recentVersion to database")
                database.insert(recentVersion.mapToEntity())
            }
            recentVersions
        }
    }

    fun clearDatabase() {
        scope.launch {
            database.clear()
        }
    }
}
