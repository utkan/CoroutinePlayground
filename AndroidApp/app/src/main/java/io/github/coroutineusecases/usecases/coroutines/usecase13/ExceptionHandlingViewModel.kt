package io.github.coroutineusecases.usecases.coroutines.usecase13

import androidx.lifecycle.viewModelScope
import io.github.coroutineusecases.base.BaseViewModel
import io.github.coroutineusecases.mock.MockApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import timber.log.Timber

class ExceptionHandlingViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun handleExceptionWithTryCatch() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                api.getAndroidVersionFeatures(27)
            } catch (exception: Exception) {
                uiState.value = UiState.Error("Network Request failed: $exception")
            }
        }
    }

    fun handleWithCoroutineExceptionHandler() {
        val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            uiState.value = UiState.Error("Network request failed!! $throwable")
        }

        uiState.value = UiState.Loading

        viewModelScope.launch(coroutineExceptionHandler) {
            api.getAndroidVersionFeatures(27)
        }
    }

    fun showResultsEvenIfChildCoroutineFails() {

        uiState.value = UiState.Loading

        viewModelScope.launch {

            supervisorScope {
                val oreoFeaturesDeferred = async { api.getAndroidVersionFeatures(27) }
                val pieFeaturesDeferred = async { api.getAndroidVersionFeatures(28) }
                val android10FeaturesDeferred = async { api.getAndroidVersionFeatures(29) }

                val versionFeatures = listOf(oreoFeaturesDeferred, pieFeaturesDeferred, android10FeaturesDeferred)
                    .mapNotNull {
                        try {
                            it.await()
                        } catch (exception: Throwable) {
                            // We have to re-throw cancellation exceptions so that
                            // our Coroutine gets cancelled immediately.
                            // Otherwise, the CancellationException is ignored
                            // and the Coroutine keeps running until it reaches the next
                            // suspension point.
                            if (exception is CancellationException) {
                                throw exception
                            }
                            Timber.e("Error loading feature data!")
                            null
                        }
                    }
                uiState.value = UiState.Success(versionFeatures)
            }
        }

    }

}
