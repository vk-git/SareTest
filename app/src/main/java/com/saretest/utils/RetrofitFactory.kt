package com.saretest.utils;

import android.content.Context
import com.google.gson.GsonBuilder
import com.saretest.BuildConfig
import com.saretest.utils.ConnectivityInterceptor
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    fun getRetrofit(application: Context, API: String): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val httpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }

        httpClient.interceptors().add(ConnectivityInterceptor(application))
        httpClient.connectTimeout(1, TimeUnit.MINUTES).writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)

        return Retrofit.Builder()
            .baseUrl(API)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
}
