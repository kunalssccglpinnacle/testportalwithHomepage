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
    val exam_mode_id: String,
    val Questions: String,
    val Marks: String,
    val Time: String,
    val Title: String,
    val PaperStatus: String,
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

// Data classes for save_next
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

// This piece has to be modified in the future as it is the request for the index
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

data class AttemptedRequest(
    val paper_code: String,
    val email_id: String,
    val exam_mode_id: String,
    val test_series_id: String
)

data class AttemptedResponse(
    val Rank: Int,
    val TotalStudent: Int,
    val Score: Double,
    val TotalMarks: Double,
    val Attempted: Int,
    val TotalQuestions: Int,
    val Accuracy: String,
    val Percentile: String,
    val Totaltime: Int,
    val TotalTimeTaken: String,
    val Subjects: List<SubjectResult>,
    val compare: List<Comparison>,
    val transformedData: TransformedData,
    val TopRanker: List<TopRanker>,
    val graph: List<Graph>,
    val your_score_perc: String,
    val y_total_attempted: String,
    val your_time_perc: String,
    val ur: String?,
    val ews: String?,
    val obc: String?,
    val sc: String?,
    val st: String?,
    val oh: String?,
    val hh: String?,
    val vh: String?,
    val Others_PWD: String?,
    val esm: String?,
    val cutoff_status: Int,
    val averageMarks: Double,
    val medianTotalMarks: Double,
    val ratingData: RatingData
)

data class SubjectResult(
    val subject_id: Int,
    val SubjectName: String,
    val SubScore: Double,
    val SubTotalMarks: Double,
    val SubAttempted: Int,
    val SubjectCorrectAttempted: Int,
    val SubjectCorrectAttemptedPerc: String,
    val SubjectWrongAttempted: Int,
    val SubjectWrongAttemptedPerc: String,
    val SubTotalQuestion: Int,
    val SubAccuracy: String,
    val SubTakingTime: String,
    val SubTotalTime: Int,
    val score_percentage: String,
    val attempted_percentage: String,
    val time_percentage: String
)

data class Comparison(
    val TotalScore: Double,
    val TotalTime: Int,
    val YourScore: Double,
    val your_score_perc: String,
    val YourCorrect: Int,
    val your_correct_perc: String,
    val YourWrong: Int,
    val your_wrong_perc: String,
    val YourTime: String,
    val your_time_perc: String,
    val YourAccuracy: String,
    val TopperScore: Double,
    val topper_score_perc: String,
    val TopperCorrect: Int,
    val topper_correct_perc: String,
    val TopperWrong: Int,
    val topper_wrong_perc: String,
    val TopperTime: String,
    val topper_time_perc: String,
    val TopperAccuracy: String,
    val topperSubjects: List<SubjectResult>
)

data class TransformedData(
    val overall: Overall,
    val subjects: List<SubjectComparison>
)

data class Overall(
    val user: ScoreDetail,
    val topper: ScoreDetail
)

data class ScoreDetail(
    val score: Double,
    val total_marks: Double,
    val accuracy: String,
    val correct: Int,
    val total_questions: Int,
    val wrong: Int,
    val time_taken: String,
    val total_time: Int,
    val score_percentage: String,
    val correct_percentage: String,
    val wrong_percentage: String,
    val time_percentage: String
)

data class SubjectComparison(
    val SubjectName: String,
    val user: ScoreDetail,
    val topper: ScoreDetail
)

data class TopRanker(
    val Name: String,
    val RankerMarks: Double
)

data class Graph(
    val label: List<String>,
    val data: List<Int>
)

data class RatingData(
    val _id: String,
    val rating: Int
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

    @POST("/attempted")
    suspend fun fetchAttempted(@Body request: AttemptedRequest): List<AttemptedResponse>
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
