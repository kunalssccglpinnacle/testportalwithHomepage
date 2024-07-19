package com.ssccgl.pinnacle.testportal.repository

import com.ssccgl.pinnacle.testportal.network.*

class TestRepository(private val apiService: ApiService) {

    suspend fun fetchTestSeries(request: TestSeriesRequest): List<TestSeriesResponse> {
        return apiService.fetchTestSeries(request)
    }

    suspend fun fetchTestSeriesDetails2(request: TestSeriesDetails2Request): TestSeriesDetails2Response {
        return apiService.getTestSeriesDetails2(request)
    }
}
