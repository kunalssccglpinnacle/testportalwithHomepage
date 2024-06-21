package com.ssccgl.pinnacle.testportal.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class IndividualExamTestPass(
    val _id: String,
    val id: Int,
    val exam_post_name: String
)

data class IndividualExamTestPassResponse(
    val examData: List<IndividualExamTestPass>
)

data class TestPass(
    val _id: String,
    val price: Int,
    val max_price: Int,
    val name: String,
    val exam: String,
    val exam_id: Int,
    val exam_mode_id: Int,
    val timestamp: String
)

interface ApiService {
    @GET("testpass")
    suspend fun getTestPasses(): List<TestPass>

    @POST("individualexamtestpass")
    suspend fun getIndividualExamTestPass(@Body request: Map<String, Int>): IndividualExamTestPassResponse
}

object RetrofitInstance {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://3.111.199.93:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
