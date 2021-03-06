package com.example.gustavobatista.paygen.service

import com.example.gustavobatista.paygen.BuildConfig
import com.example.gustavobatista.paygen.prefs
import com.example.gustavobatista.paygen.service.converters.ResponseConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by Gustavo on 10/16/2017.
 */

open class Service {
    companion object {
        private val builder = Retrofit.Builder()
                .baseUrl(BuildConfig.URL_BASE)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ResponseConverterFactory(GsonConverterFactory.create()))
                .addConverterFactory(GsonConverterFactory.create())


        fun <S> createService(serviceClass: Class<S>): S {
            val retrofit = builder
                    .build()
            return retrofit.create(serviceClass)
        }

        private val okHttpClient: OkHttpClient
            get() = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build()


        private val interceptor: Interceptor
            get() = Interceptor { chain ->
                val original = chain.request()

                val request = original.newBuilder()
                        .header("Access-Token", prefs.token)
                        .method(original.method(), original.body())
                        .build()

                chain.proceed(request)
            }
    }

}
