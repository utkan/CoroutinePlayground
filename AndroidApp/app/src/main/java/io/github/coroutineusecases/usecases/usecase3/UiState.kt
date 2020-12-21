package io.github.coroutineusecases.usecases.usecase3

import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures

sealed class UiState {
    object Loading : UiState()
    data class Success(
        val versionFeatures: List<VersionFeatures>
    ) : UiState()

    data class Error(val message: String) : UiState()
}
