package io.github.coroutineusecases.usecases.coroutines.usecase4

import android.os.Bundle
import androidx.activity.viewModels
import io.github.coroutineusecases.R
import io.github.coroutineusecases.base.BaseActivity
import io.github.coroutineusecases.base.useCase4Description
import io.github.coroutineusecases.databinding.ActivityPerformvariableamountofnetworkrequestsconcurrentlyBinding
import io.github.coroutineusecases.fromHtml
import io.github.coroutineusecases.setGone
import io.github.coroutineusecases.setVisible
import io.github.coroutineusecases.toast

class VariableAmountOfNetworkRequestsActivity : BaseActivity() {

    private val binding by lazy {
        ActivityPerformvariableamountofnetworkrequestsconcurrentlyBinding.inflate(
            layoutInflater
        )
    }

    private val viewModel: VariableAmountOfNetworkRequestsViewModel by viewModels()

    override fun getToolbarTitle() = useCase4Description

    private var operationStartTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.uiState().observe(this, { uiState ->
            if (uiState != null) {
                render(uiState)
            }
        })

        binding.btnRequestsSequentially.setOnClickListener {
            viewModel.performNetworkRequestsSequentially()
        }

        binding.btnRequestsConcurrently.setOnClickListener {
            viewModel.performNetworkRequestsConcurrently()
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
        operationStartTime = System.currentTimeMillis()
        progressBar.setVisible()
        textViewResult.text = ""
        textViewDuration.text = ""
        disableButtons()
    }

    private fun onSuccess(uiState: UiState.Success) = with(binding) {
        enableButtons()
        progressBar.setGone()
        val duration = System.currentTimeMillis() - operationStartTime
        textViewDuration.text = getString(R.string.duration, duration)

        val versionFeatures = uiState.versionFeatures
        val versionFeaturesString = versionFeatures.map {
            "<b>New Features of ${it.androidVersion.name} </b> <br> ${
                it.features.joinToString(
                    prefix = "- ",
                    separator = "<br>- "
                )
            }"
        }.joinToString(separator = "<br><br>")

        textViewResult.text = fromHtml(versionFeaturesString)
    }

    private fun onError(uiState: UiState.Error) = with(binding) {
        progressBar.setGone()
        toast(uiState.message)
        enableButtons()
    }

    private fun enableButtons() = with(binding) {
        btnRequestsSequentially.isEnabled = true
        btnRequestsConcurrently.isEnabled = true
    }

    private fun disableButtons() = with(binding) {
        btnRequestsSequentially.isEnabled = false
        btnRequestsConcurrently.isEnabled = false
    }
}
