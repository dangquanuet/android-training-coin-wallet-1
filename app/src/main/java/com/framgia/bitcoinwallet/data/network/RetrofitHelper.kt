package com.framgia.bitcoinwallet.data.network

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {
    private const val CONNECTION_TIMEOUT = 10

    fun <T : Any> createService(application: Application, endPoint: String, serviceClass: Class<T>): T {
        val httpClientBuilder = OkHttpClient.Builder()
        val cacheSize = 30 * 1024 * 1024 // 10 MiB
        httpClientBuilder.cache(Cache(application.cacheDir, cacheSize.toLong()))

        httpClientBuilder.readTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
        httpClientBuilder.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
        val builder = Retrofit.Builder().baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create(getGsonConfig()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
        val retrofit = builder.client(httpClientBuilder.build()).build()
        return retrofit.create(serviceClass)
    }

    fun getGsonConfig(): Gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
}
