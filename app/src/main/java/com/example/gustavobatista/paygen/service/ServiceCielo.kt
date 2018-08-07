package com.example.gustavobatista.paygen.service

import com.example.gustavobatista.paygen.BuildConfig
import com.example.gustavobatista.paygen.service.converters.ResponseConverterFactory

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by Gustavo on 10/16/2017.
 */

open class ServiceCielo {
    companion object {
        private val builder = Retrofit.Builder()
                .baseUrl(BuildConfig.URL_CIELO)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())


        fun <S> createService(serviceClass: Class<S>): S {
            val retrofit = builder
                    .build()
            return retrofit.create(serviceClass)
        }
    }

}
