
package com.ssccgl.pinnacle.testportal.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class TestPass(
    val Product_title: String,
    val id: Int,
    val totalTestSeries: Int,
    val product_price: Int,
    val product_max_price: Int,
    val product_validation_period: Int
)

data class IndividualExamTestPass(
    val post_tier_name: String,
    val id: Int,
    val exam_post_id: Int,
    val exam_id: Int,
    val mockTestCount: Int,
    val sectionalTestCount: Int,
    val chapterwiseTestCount: Int,
    val previousYearTestCount: Int
)


data class NewTestsWebRequest(
    val email_id: String,
    val exam_post_tier_id: String,
    val exam_id: String,
    val tier_id: String
)

data class NewTestsWebResponse(
    val ExamPost: String,
    val ExamPostTier: String,
    val TestType: List<TestType>
)

data class TestType(
    val test_type: String,
    val exam_mode_id: Int,
    val TotalTests: Int,
    val FreeTests: Int,
    val TotalTestSeries: Int
)

data class TestSeriesRequest(
    val email_id: String,
    val exam_mode_id: String,
    val exam_post_id: String
)

data class TestSeriesResponse(
    val total_test: Int,
    val exam_logo: String,
    val free_total_test: Int,
    val test_series_name: String,
    val test_series_id: Int
)



data class TestSeriesDetails2Request(
    val email_id: String,
    val test_series_id: String
)

data class TestSeriesDetails2Response(
    val test_series_name: String,
    val ar: List<AR>
)

data class AR(
        val test_series_id: String,
        val paper_code: String,
        val exam_mode_id: Int,
        val Questions: Int,
        val Marks: Int,
        val Time: Int,
        val Title: String,
        val PaperStatus: Int,
        val RemainingTime: String,
        val languages: String,
        val expire_date: String,
        val expiry_time: String?,
        val paid_status: Int,
        val start_date: String,
        val start_time: String,
        val free_status: String
    )


    // Data classes for index

data class Subject(
        val sb_id: Int,
        val ppr_id: Int,
        val subject_name: String
    )


data class Detail(
        val qid: Int,
        val question_id: Int,
        val subject_id: Int,
        val question: String,
        val option1: String,
        val option2: String,
        val option3: String,
        val option4: String,
        val hindi_question: String,
        val positive_marks: String,
        val negative_marks: Double,
        val answered_ques: Int,
        val hrs: String,
        val mins: String,
        val secs: String,
        val answer: String
    )


data class IndexResponse(
        val subjects: List<Subject>,
        val details: List<Detail>
    )

// data classes for save_next


data class SaveAnswerRequest(
        val paper_code: String,
        val paper_id: String,
        val option: String,
        val answer_status: String,
        val email_id: String,
        val SingleTm: String,
        val rTem: String,
        val test_series_id: String,
        val exam_mode_id: String,
        val subject: Int,
        val CurrentPaperId: Int,
        val SaveType: String
    )


data class SaveAnswerResponse(
        val sub_id: Int,
        val answer_status: Int,
        val answer_status_new: Int,
        val answered_count: Int,
        val notanswered_count: Int,
        val marked_count: Int,
        val marked_answered_count: Int,
        val not_visited: Int,
        val paper_ids: Int,
        val status: Int,
        val subjectname: String,
        val choosed_answer: String
    )


// This peice has to be modified in the future as it is the request for the index
data class FetchDataRequest(
        val paper_code: String,
        val email_id: String,
        val exam_mode_id: String,
        val test_series_id: String
    )


// Data classes for submit request and response
data class SubmitRequest(
        val email_id: String,
        val paper_code: String,
        val exam_mode_id: String,
        val test_series_id: String,
        val rTem: String
    )


data class SubmitResponse(
        val status: String
    )

data class PaperCodeDetailsResponse(
    val paper_code: String,
    val title: String,
    val subject_id: Int,
    val questions: Int,
    val hrs: Int,
    val mins: Int,
    val secs: Int,
    val subject_name: String,
    val answered_count: Int,
    val notanswered_count: Int,
    val marked_count: Int,
    val marked_answered_count: Int,
    val not_visited: Int,
    val test_type: String
)











interface ApiService {
    @GET("testpass")
    suspend fun getTestPasses(): List<TestPass>

    @POST("IndividualExamTestPass")
    suspend fun getIndividualExamTestPass(@Body params: Map<String, Int>): List<IndividualExamTestPass>


    @POST("NewTestsWeb")
    suspend fun getNewTestsWeb(@Body params: List<NewTestsWebRequest>): List<NewTestsWebResponse>

    @POST("TestSeries")
    suspend fun fetchTestSeries(@Body request: TestSeriesRequest): List<TestSeriesResponse>


    @POST("TestSeriesDetails2")
    suspend fun getTestSeriesDetails2(@Body request: TestSeriesDetails2Request): TestSeriesDetails2Response




    @POST("/index")
    suspend fun fetchData(@Body request: FetchDataRequest): List<IndexResponse>

    @POST("/save_next")
    suspend fun saveAnswer(@Body request: SaveAnswerRequest): SaveAnswerResponse

    @POST("/submit")
    suspend fun submit(@Body request: SubmitRequest): SubmitResponse

    @POST("/paperCodeDetails")
    suspend fun fetchPaperCodeDetails(@Body request: FetchDataRequest): PaperCodeDetailsResponse


//    @POST("get_new_tests_web_endpoint") // Update with your actual endpoint
//    suspend fun getNewTestsWeb(@Body request: List<NewTestsWebRequest>): List<NewTestsWebResponse>
//
//    @POST("fetch_test_series_endpoint") // Update with your actual endpoint
//    suspend fun fetchTestSeries(@Body request: TestSeriesRequest): List<TestSeriesResponse>
//
//    @POST("get_test_series_details2_endpoint") // Update with your actual endpoint
//    suspend fun getTestSeriesDetails2(@Body request: TestSeriesDetails2Request): TestSeriesDetails2Response

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



