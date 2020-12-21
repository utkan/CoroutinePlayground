package io.github.coroutineusecases.usecases.coroutines.usecase5

import androidx.lifecycle.viewModelScope
import io.github.coroutineusecases.base.BaseViewModel
import io.github.coroutineusecases.mock.MockApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull

class NetworkRequestWithTimeoutViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest(timeOut: Long) {
        uiState.value = UiState.Loading

//        usingWithTimeout(timeOut)
//
        usingWithTimeoutOrNull(timeOut)
    }

    private fun usingWithTimeoutOrNull(timeOut: Long) {
        viewModelScope.launch {
            try {
                val recentVersions = withTimeoutOrNull(timeOut) {
                    api.getRecentAndroidVersions()
                }
                if (recentVersions != null) {
                    uiState.value = UiState.Success(recentVersions)
                } else {
                    uiState.value = UiState.Error("Network Request timed out!")
                }
            } catch (timeoutCancellationException: TimeoutCancellationException) {
                uiState.value = UiState.Error("Network Request timed out!")
            } catch (exception: Exception) {
                uiState.value = UiState.Error("Network Request failed!")
            }
        }
    }

    private fun usingWithTimeout(timeOut: Long) {
        viewModelScope.launch {
            try {
                val recentVersions = withTimeout(timeOut) {
                    api.getRecentAndroidVersions()
                }
                uiState.value = UiState.Success(recentVersions)
            } catch (timeoutCancellationException: TimeoutCancellationException) {
                uiState.value = UiState.Error("Network Request timed out!")
            } catch (exception: Exception) {
                uiState.value = UiState.Error("Network Request failed!")
            }
        }
    }

}
