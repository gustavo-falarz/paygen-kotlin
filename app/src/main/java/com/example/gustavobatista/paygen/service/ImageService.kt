package com.example.gustavobatista.paygen.service

import com.example.gustavobatista.paygen.BuildConfig
import com.example.gustavobatista.paygen.service.endpoint.ImageEndpoint
import com.example.gustavobatista.paygen.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class ImageService {


    companion object {
        private val service: ImageEndpoint
            get() = createService(ImageEndpoint::class.java)

        fun uploadImage(file: String) = service.uploadImage(file, Constants.UPLOAD_PRESET)

        private val builder = Retrofit.Builder()
                .baseUrl(BuildConfig.URL_CLOUDINARY)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())


        private fun <S> createService(serviceClass: Class<S>): S {
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