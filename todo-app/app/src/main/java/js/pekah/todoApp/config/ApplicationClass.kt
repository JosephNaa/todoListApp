package js.pekah.todoApp.config

import android.app.Application
//import js.pekah.todoApp.database.TodoDatabase
//import js.pekah.todoApp.repository.TodoRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass: Application() {

    companion object {
        const val SERVER_URL = "http://127.0.0.1:8080/"

        lateinit var retrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS).build()

        retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}