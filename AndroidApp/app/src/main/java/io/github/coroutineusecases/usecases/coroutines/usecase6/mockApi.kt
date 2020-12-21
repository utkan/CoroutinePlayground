package io.github.coroutineusecases.usecases.coroutines.usecase6

import com.google.gson.Gson
import com.lukaslechner.coroutineusecasesonandroid.mock.mockAndroidVersions
import io.github.coroutineusecases.mock.createMockApi
import io.github.coroutineusecases.util.MockNetworkInterceptor

fun mockApi() = createMockApi(
    MockNetworkInterceptor()
        .mock(
            "http://localhost/recent-android-versions",
            "something went wrong on server side",
            500,
            1000,
            persist = false
        ).mock(
            "http://localhost/recent-android-versions",
            "something went wrong on server side",
            500,
            1000,
            persist = false
        ).mock(
            "http://localhost/recent-android-versions",
            Gson().toJson(mockAndroidVersions),
            200,
            1000
        )
)
