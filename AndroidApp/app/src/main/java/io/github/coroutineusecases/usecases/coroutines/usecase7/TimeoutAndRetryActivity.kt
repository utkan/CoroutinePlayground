package io.github.coroutineusecases.usecases.coroutines.usecase7

import android.os.Bundle
import androidx.activity.viewModels
import io.github.coroutineusecases.base.BaseActivity
import io.github.coroutineusecases.base.useCase7Description
import io.github.coroutineusecases.databinding.ActivityRetrynetworkrequestBinding
import io.github.coroutineusecases.fromHtml
import io.github.coroutineusecases.setGone
import io.github.coroutineusecases.setVisible
import io.github.coroutineusecases.toast

class TimeoutAndRetryActivity : BaseActivity() {

    override fun getToolbarTitle() = useCase7Description

    private val binding by lazy { ActivityRetrynetworkrequestBinding.inflate(layoutInflater) }
    private val viewModel: TimeoutAndRetryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.uiState().observe(this, { uiState ->
            if (uiState != null) {
                render(uiState)
            }
        })
        binding.btnPerformSingleNetworkRequest.setOnClickListener {
            viewModel.performNetworkRequest()
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
        btnPerformSingleNetworkRequest.isEnabled = false
    }

    private fun onSuccess(uiState: UiState.Success) = with(binding) {
        progressBar.setGone()
        btnPerformSingleNetworkRequest.isEnabled = true

        val versionFeatures = uiState.versionFeatures
        val versionFeaturesString = versionFeatures.map {
            "<b>New Features of ${it.androidVersion.name} </b> <br> ${it.features.joinToString(
                prefix = "- ",
                separator = "<br>- "
            )}"
        }.joinToString(separator = "<br><br>")

        textViewResult.text = fromHtml(versionFeaturesString)
    }

    private fun onError(uiState: UiState.Error) = with(binding) {
        progressBar.setGone()
        btnPerformSingleNetworkRequest.isEnabled = true
        toast(uiState.message)
    }
}
