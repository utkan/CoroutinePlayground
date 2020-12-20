package io.github.coroutineusecases.usecases.coroutines.usecase2

import androidx.lifecycle.viewModelScope
import io.github.coroutineusecases.base.BaseViewModel
import io.github.coroutineusecases.mock.MockApi
import io.github.coroutineusecases.usecases.coroutines.usecase1.mockApi
import kotlinx.coroutines.launch
import timber.log.Timber

class Perform2SequentialNetworkRequestsViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun perform2SequentialNetworkRequest() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val recentAndroidVersions = mockApi.getRecentAndroidVersions()
                val mostRecentVersion = recentAndroidVersions.last()

                val androidVersionFeatures = mockApi.getAndroidVersionFeatures(mostRecentVersion.apiLevel)
                uiState.value = UiState.Success(androidVersionFeatures)
            } catch (t: Throwable) {
                Timber.e(t)
                uiState.value = UiState.Error("Network Request failed")
            }
        }
    }
}
