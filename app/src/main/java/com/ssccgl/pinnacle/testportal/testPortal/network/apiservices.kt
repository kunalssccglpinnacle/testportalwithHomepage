package com.ssccgl.pinnacle.testportal.network



import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST








data class IndividualExamTestPass(
    val email_id: String,
    val post_tier_id: String,
    val exam_id: String,
    val tier_id: String,
    val post_tier_name: String,
    val mockTestCount: Int,
    val sectionalTestCount: Int,
    val chapterwiseTestCount: Int,
    val previousYearTestCount: Int
)

data class TestPass(
    val Product_title: String,
    val id: Int,
    val totalTestSeries: Int,
    val product_price: Int,
    val product_max_price: Int,
    val product_validation_period: Int
)

interface ApiService {
    @GET("testpass")
    suspend fun getTestPasses(): List<TestPass>

    @POST("IndividualExamTestPass")
    suspend fun getIndividualExamTestPass(@Body request: Map<String, Int>): List<IndividualExamTestPass>

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


