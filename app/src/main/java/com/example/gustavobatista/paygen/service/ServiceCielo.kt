package com.example.gustavobatista.paygen.service

import com.example.gustavobatista.paygen.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by Gustavo on 10/16/2017.
 */

open class ServiceCielo {
    companion object {
        private val builder = Retrofit.Builder()
                .baseUrl(BuildConfig.URL_CIELO)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())


        fun <S> createService(serviceClass: Class<S>): S {
            val retrofit = builder
                    .build()
            return retrofit.create(serviceClass)
        }

        private val okHttpClient: OkHttpClient
            get() = OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build()
    }

}
