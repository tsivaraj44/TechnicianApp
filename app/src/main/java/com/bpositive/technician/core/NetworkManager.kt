package com.bpositive.technician.core

import com.bpositive.BuildConfig
import com.bpositive.technician.utils.BaseData.BASE_URL
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkManager {

    companion object {
        private var baseURL: String = BASE_URL
        private var serviceClass: Any? = null

        fun baseURL(baseURL: String) = apply {
            this.baseURL = baseURL
        }

        fun <T> serviceClass(serviceClass: Class<T>?) = apply {
            this.serviceClass = serviceClass
        }

        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        //init Retrofit base url
        fun <T> create(): T {

            val client = OkHttpClient.Builder().apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(loggingInterceptor)
                    addNetworkInterceptor(StethoInterceptor())
                }
                connectTimeout(30, TimeUnit.SECONDS)
                readTimeout(30, TimeUnit.SECONDS)
                writeTimeout(30, TimeUnit.SECONDS)
            }.build()

            return Retrofit.Builder()
                .client(client)
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build().create(serviceClass as Class<T>)
        }


    }

}