package io.github.coroutineusecases.usecases.coroutines.usecase4

import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures
import io.github.coroutineusecases.mock.MockApi
import io.github.coroutineusecases.utils.EndpointShouldNotBeCalledException
import kotlinx.coroutines.delay
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

class FakeVersionsErrorApi(private val responseDelay: Long) : MockApi {

    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        delay(responseDelay)
        throw throw HttpException(
            Response.error<List<AndroidVersion>>(
                500,
                ResponseBody.create(MediaType.parse("application/json"), "")
            )
        )
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        throw EndpointShouldNotBeCalledException()
    }
}
