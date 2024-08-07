package com.ssccgl.pinnacle.testportal.repository

import android.util.Log
import com.ssccgl.pinnacle.testportal.network.*
import com.ssccgl.pinnacle.testportal.network.RetrofitInstance.api

class TestRepository(private val apiService: ApiService) {



    suspend fun fetchTestSeries(request: TestSeriesRequest): List<TestSeriesResponse> {
        return apiService.fetchTestSeries(request)
    }

    suspend fun fetchTestSeriesDetails2(request: TestSeriesDetails2Request): TestSeriesDetails2Response {
        return apiService.getTestSeriesDetails2(request)
    }




    suspend fun checkTestSeriesAccess(request: List<TestSeriesAccessRequest>): TestSeriesAccessResponse {
        return api.checkTestSeriesAccess(request)
    }
}


