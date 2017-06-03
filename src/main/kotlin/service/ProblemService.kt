package service

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProblemService {
    @GET("problem.page")
    fun problemPage(@Query("gpid") gpid: String): Call<String>

    @GET("refcode.page")
    fun refCodePage(@Query("gpid") gpid: String): Call<String>

    @POST("problem.TrainProblems.dt")
    fun problems(): Call<JsonObject>
}
