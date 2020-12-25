package io.github.coroutineusecases.usecases.coroutines.usecase11

import android.os.Bundle
import androidx.activity.viewModels
import io.github.coroutineusecases.R
import io.github.coroutineusecases.base.BaseActivity
import io.github.coroutineusecases.base.useCase11Description
import io.github.coroutineusecases.databinding.ActivityCooperativecancellationBinding
import io.github.coroutineusecases.hideKeyboard
import io.github.coroutineusecases.setGone
import io.github.coroutineusecases.setVisible
import io.github.coroutineusecases.toast

class CooperativeCancellationActivity : BaseActivity() {

    override fun getToolbarTitle() = useCase11Description

    private val binding by lazy { ActivityCooperativecancellationBinding.inflate(layoutInflater) }
    private val viewModel: CooperativeCancellationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.uiState().observe(this, { uiState ->
            if (uiState != null) {
                render(uiState)
            }
        })
        binding.btnCalculate.setOnClickListener {
            val factorialOf = binding.editTextFactorialOf.text.toString().toIntOrNull()
            if (factorialOf != null) {
                viewModel.performCalculation(factorialOf)
            }
        }
        binding.btnCancel.setOnClickListener {
            viewModel.cancelCalculation()
        }
    }

    private fun render(uiState: UiState) {
        when (uiState) {
            is UiState.Loading -> {
                onLoad()
            }
            is UiState.Success -> {
                onSuccess(uiState)
            }
            is UiState.Error -> {
                onError(uiState)
            }
        }
    }

    private fun onLoad() = with(binding) {
        progressBar.setVisible()
        textViewResult.text = ""
        textViewCalculationDuration.text = ""
        textViewStringConversionDuration.text = ""
        btnCalculate.isEnabled = false
        btnCancel.isEnabled = true
        textViewResult.hideKeyboard()
    }

    private fun onSuccess(uiState: UiState.Success) = with(binding) {
        textViewCalculationDuration.text =
            getString(R.string.duration_calculation, uiState.computationDuration)

        textViewStringConversionDuration.text =
            getString(R.string.duration_stringconversion, uiState.stringConversionDuration)

        binding.progressBar.setGone()
        btnCalculate.isEnabled = true
        btnCancel.isEnabled = false

        textViewResult.text = if (uiState.result.length <= 150) {
            uiState.result
        } else {
            "${uiState.result.substring(0, 147)}..."
        }
    }

    private fun onError(uiState: UiState.Error) = with(binding) {
        progressBar.setGone()
        btnCalculate.isEnabled = true
        btnCancel.isEnabled = false
        toast(uiState.message)
    }
}
