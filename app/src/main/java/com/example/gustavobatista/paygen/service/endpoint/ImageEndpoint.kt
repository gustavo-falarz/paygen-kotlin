package com.example.gustavobatista.paygen.service.endpoint

import com.example.gustavobatista.paygen.entity.CloudinaryResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ImageEndpoint {
    @FormUrlEncoded
    @POST("image/upload")
    fun uploadImage(@Field("file") file: String,
                     @Field("upload_preset") preset: String): Observable<CloudinaryResponse>
}
