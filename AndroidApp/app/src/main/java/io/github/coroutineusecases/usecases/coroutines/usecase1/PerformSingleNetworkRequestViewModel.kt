package io.github.coroutineusecases.usecases.coroutines.usecase1

import androidx.lifecycle.viewModelScope
import io.github.coroutineusecases.base.BaseViewModel
import io.github.coroutineusecases.mock.MockApi
import kotlinx.coroutines.launch
import timber.log.Timber

class PerformSingleNetworkRequestViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performSingleNetworkRequest() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val recentAndroidVersions = mockApi.getRecentAndroidVersions()
                uiState.value = UiState.Success(recentAndroidVersions)
            } catch (e: Throwable) {
                Timber.e(e)
                uiState.value = UiState.Error("Network Request failed!")
            }
        }
    }
}
