package com.ssccgl.pinnacle.testportal.repository

import com.ssccgl.pinnacle.testportal.network.*

class TestRepository(private val apiService: ApiService) {

//    suspend fun fetchData(request: TestSeriesDetails2Response.FetchDataRequest): List<TestSeriesDetails2Response.ApiResponse>? {
//        return apiService.fetchData(request)
//    }
//
//    suspend fun saveAnswer(request: TestSeriesDetails2Response.SaveAnswerRequest): TestSeriesDetails2Response.SaveAnswerResponse? {
//        return apiService.saveAnswer(request)
//    }
//
//    suspend fun submit(request: TestSeriesDetails2Response.SubmitRequest): TestSeriesDetails2Response.SubmitResponse? {
//        return apiService.submit(request)
//    }
//
//    suspend fun getNewTestsWeb(request: List<NewTestsWebRequest>): List<NewTestsWebResponse> {
//        return apiService.getNewTestsWeb(request)
//    }

    suspend fun fetchTestSeries(request: TestSeriesRequest): List<TestSeriesResponse> {
        return apiService.fetchTestSeries(request)
    }

    suspend fun fetchTestSeriesDetails2(request: TestSeriesDetails2Request): TestSeriesDetails2Response {
        return apiService.getTestSeriesDetails2(request)
    }
}
