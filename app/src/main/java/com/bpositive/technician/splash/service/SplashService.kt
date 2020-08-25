package com.bpositive.technician.splash.service

import com.bpositive.technician.splash.model.GetCoreResponse
import com.bpositive.technician.utils.BaseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SplashService {

//    http://192.168.1.104:1041/coreapiv1?type=getcoreconfig


    @GET("index")
    fun callGetCore(
        @Query("type") type: String,
        @Query("lang") lang: String = BaseData.DEFAULT_LANG_CODE
    ): Call<GetCoreResponse>



    /*companion object Factory {

        private val mInterceptor = run {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }

        }

        private val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(mInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        val BASE_URL = "https://api.github.com/"

        fun create(): SplashService {
            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(SplashService::class.java)
        }
    }*/

}