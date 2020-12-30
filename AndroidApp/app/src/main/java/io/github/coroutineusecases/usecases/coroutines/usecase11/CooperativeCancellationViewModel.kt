package io.github.coroutineusecases.usecases.coroutines.usecase11

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import timber.log.Timber
import java.math.BigInteger
import kotlin.system.measureTimeMillis

class CooperativeCancellationViewModel(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    private var calculationJob: Job? = null

    fun performCalculation(factorialOf: Int) {
        uiState.value = UiState.Loading
        calculationJob = viewModelScope.launch {
            try {

                var result: BigInteger
                val computationDuration = measureTimeMillis {
                    result = calculateFactorialOf(factorialOf)
                }

                var resultString = ""
                val stringConversionDuration = measureTimeMillis {
                    resultString = convertToString(result)
                }

                uiState.value =
                    UiState.Success(resultString, computationDuration, stringConversionDuration)
            } catch (exception: Exception) {
                uiState.value = if (exception is CancellationException) {
                    UiState.Error("Calculation was cancelled")
                } else {
                    UiState.Error("Error while calculating result")
                }
            }
        }

        calculationJob?.invokeOnCompletion {
            if (it is RecyclerView.ViewCacheExtension) {
                UiState.Error("Calculation was cancelled")
            }
        }
    }

    // factorial of n (n!) = 1 * 2 * 3 * 4 * ... * n
    private suspend fun calculateFactorialOf(number: Int): BigInteger =
        withContext(defaultDispatcher) {
            var factorial = BigInteger.ONE
            for (i in 1..number) {

                // yield enables cooperative cancellations
                // alternatives:
                // - ensureActive()
                // - isActive() - possible to do clean up tasks with
                yield()

                factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
            }
            Timber.d("Calculating Factorial completed")
            factorial
        }

    private suspend fun convertToString(number: BigInteger): String =
        withContext(defaultDispatcher) {
            number.toString()
        }

    fun uiState(): LiveData<UiState> = uiState

    fun cancelCalculation() {
        calculationJob?.cancel()
    }

    private val uiState: MutableLiveData<UiState> = MutableLiveData()
}
