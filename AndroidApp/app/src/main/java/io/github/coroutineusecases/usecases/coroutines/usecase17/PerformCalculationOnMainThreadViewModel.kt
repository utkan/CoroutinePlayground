package io.github.coroutineusecases.usecases.coroutines.usecase17

import androidx.lifecycle.viewModelScope
import io.github.coroutineusecases.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import java.math.BigInteger
import kotlin.system.measureTimeMillis

class PerformCalculationOnMainThreadViewModel : BaseViewModel<UiState>() {
    fun performCalculationWithDefaultDispatcher(factorialOf: Int) {
        uiState.value = UiState.Loading
        viewModelScope.launch {

            val duration = measureTimeMillis {
                calculateFactorialOnDefaultDispatcher(factorialOf)
            }

            uiState.value = UiState.Success("Default Dispatcher", duration)
        }
    }

    fun performCalculationOnMainThreadUsingYield(factorialOf: Int) {
        uiState.value = UiState.Loading

        viewModelScope.launch {
            val duration = measureTimeMillis {
                calculateFactorialInMainThreadUsingYield(factorialOf)
            }
            uiState.value = UiState.Success("Main thread using yield()", duration)
        }
    }

    fun performCalculationOnMainThread(factorialOf: Int) {
        uiState.value = UiState.Loading

        val duration = measureTimeMillis {
            calculateFactorialInMainThread(factorialOf)
        }
        uiState.value = UiState.Success("Main thread", duration)
    }

    private fun calculateFactorialInMainThread(number: Int): BigInteger {
        var factorial = BigInteger.ONE
        for (i in 1..number) {
            factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
        }
        return factorial
    }

    private suspend fun calculateFactorialInMainThreadUsingYield(number: Int): BigInteger {
        var factorial = BigInteger.ONE
        for (i in 1..number) {
            yield()
            factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
        }
        return factorial
    }

    private suspend fun calculateFactorialOnDefaultDispatcher(number: Int): BigInteger {
        return withContext(Dispatchers.Default) {
            var factorial = BigInteger.ONE
            for (i in 1..number) {
                factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
            }
            factorial
        }
    }
}
