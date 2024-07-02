package com.ssccgl.pinnacle.testportal.network


import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
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


data class ExamPost(
    val post_name: String,
    val exam_post_tier_id: Int,
    val logo: String
)









data class IndividualExamPost(
    val _id: String,
    val id: Int,
    val order_list: Int,
    val exam_id: Int,
    val post_tier_name: String,
    val status: Int,
    val ts: String,
    val exam_post_id: Int
)

data class TestType(
    val test_type: String,
    val exam_mode_id: Int,
    val TotalTests: Int,
    val FreeTests: Int,
    val TotalTestSeries: Int
)

data class ExamPostResponse(
    val ExamPost: String,
    val ExamPostTier: String,
    val TestType: List<TestType>
)

data class ExamPostRequest(
    val email_id: String,
    val exam_post_tier_id: String,
    val exam_id: String,
    val tier_id: String
)





interface ApiService {
    @GET("testpass")
    suspend fun getTestPasses(): List<TestPass>

    @POST("individualexamtestpass")
    suspend fun getIndividualExamTestPass(@Body request: Map<String, Int>): IndividualExamTestPassResponse




    @POST("getindividualexampostdata")
    suspend fun getIndividualExamPostData(@Body examPostId: Map<String, Int>): List<IndividualExamPost>




    @POST("NewTestsWeb")
    suspend fun getExamPostData(@Body request: List<ExamPostRequest>): List<ExamPostResponse>

    @Headers("Content-Type: application/json")
    @POST("RelatedExam")
    suspend fun fetchExamPosts(@Body request: Map<String, String>): Response<List<ExamPost>>

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


//object NetworkService {
//    private const val BASE_URL = "http://3.111.199.93:5000/"
//
//    val api: ApiService by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ApiService::class.java)
//    }
