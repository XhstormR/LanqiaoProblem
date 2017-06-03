package service

import com.google.gson.Gson
import entity.Problem
import entity.ProblemDetail
import okhttp3.OkHttpClient
import org.jsoup.Jsoup
import repository.ProblemRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

object LanqiaoService {
    private val gson = Gson()
    private val service by lazy {
        val properties = Properties().apply { load(ClassLoader.getSystemResourceAsStream("123.txt")) }
        val client = OkHttpClient.Builder()
                .addInterceptor {
                    val request = it.request().newBuilder().addHeader("Cookie", properties.getProperty("Cookie")).build()
                    it.proceed(request)
                }
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl("http://lx.lanqiao.cn/")
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        retrofit.create(ProblemService::class.java)
    }

    fun getProblem(id: Int) = ProblemRepository.getProblemsById(id)

    fun getProblems() = ProblemRepository.getProblems()

    fun saveProblems() = getProblemsFromWeb().also {
        it.forEach {
            val detail = ProblemDetail().apply {
                service.problemPage(it.gpid!!).execute().body().let {
                    this.description = Jsoup.parse(it).getElementsByClass("des").toString()
                }
                service.refCodePage(it.gpid!!).execute().body().let {
                    val document = Jsoup.parse(it)
                    this.refCode_c = document.getElementById("codelinesc").toString()
                    this.refCode_cpp = document.getElementById("codelinescpp").toString()
                    this.refCode_java = document.getElementById("codelinesjava").toString()
                }
            }
            it.addDetails(detail)
        }
    }.run { ProblemRepository.saveProblems(this) }

    fun getProblemsFromWeb(): List<Problem> {
        val list = arrayListOf<Problem>()
        service.problems().execute().body()?.let {
            var i = 0
            while (true) {
                val element = it[i.toString()] ?: break
                val problem = gson.fromJson(element, Problem::class.java)
                list.add(problem)
                i++
            }
        }
        return list.sortedBy(Problem::tpid)
    }

    fun close() = ProblemRepository.close()
}
