package io.github.coroutineusecases.usecases.coroutines.usecase4

import androidx.lifecycle.viewModelScope
import io.github.coroutineusecases.base.BaseViewModel
import io.github.coroutineusecases.mock.MockApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import timber.log.Timber

class VariableAmountOfNetworkRequestsViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequestsSequentially() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val recentAndroidVersions = mockApi.getRecentAndroidVersions()
                val versionFeatures = recentAndroidVersions.map { androidVersion ->
                    mockApi.getAndroidVersionFeatures(androidVersion.apiLevel)
                }
                uiState.value = UiState.Success(versionFeatures)
            } catch (t: Throwable) {
                uiState.value = UiState.Error("Network Request failed")
                Timber.e(t)
            }
        }
    }

    fun performNetworkRequestsConcurrently() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val recentAndroidVersions = mockApi.getRecentAndroidVersions()
                val versionFeatures = recentAndroidVersions.map { androidVersion ->
                    async { mockApi.getAndroidVersionFeatures(androidVersion.apiLevel) }
                }.awaitAll()
                uiState.value = UiState.Success(versionFeatures)
            } catch (t: Throwable) {
                uiState.value = UiState.Error("Network Request failed")
                Timber.e(t)
            }
        }
    }
}
