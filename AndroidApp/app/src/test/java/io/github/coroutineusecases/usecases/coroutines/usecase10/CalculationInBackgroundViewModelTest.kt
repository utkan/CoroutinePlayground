package io.github.coroutineusecases.usecases.coroutines.usecase10

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.github.coroutineusecases.utils.MainCoroutineScopeRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class CalculationInBackgroundViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get: Rule
    val mainCoroutineScopeRule: MainCoroutineScopeRule = MainCoroutineScopeRule()

    private val receivedUiStates = mutableListOf<UiState>()

    @Test
    fun `performCalculation() should perform correct calculations`() =
        mainCoroutineScopeRule.runBlockingTest {
            val viewModel =
                CalculationInBackgroundViewModel(mainCoroutineScopeRule.testDispatcher).apply {
                    observe()
                }

            Assert.assertTrue(receivedUiStates.isEmpty())

            viewModel.performCalculation(1)

            Assert.assertEquals(
                UiState.Loading,
                receivedUiStates.first()
            )

            Assert.assertEquals(
                "1",
                (receivedUiStates[1] as UiState.Success).result
            )

            receivedUiStates.clear()

            viewModel.performCalculation(2)

            Assert.assertEquals(
                UiState.Loading,
                receivedUiStates.first()
            )

            Assert.assertEquals(
                "2",
                (receivedUiStates[1] as UiState.Success).result
            )

            receivedUiStates.clear()

            viewModel.performCalculation(3)

            Assert.assertEquals(
                UiState.Loading,
                receivedUiStates.first()
            )

            Assert.assertEquals(
                "6",
                (receivedUiStates[1] as UiState.Success).result
            )
        }

    private fun CalculationInBackgroundViewModel.observe() {
        uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiStates.add(uiState)
            }
        }
    }
}
