package com.example.gustavobatista.paygen.service.endpoint

import com.example.gustavobatista.paygen.entity.dto.LoginDTO
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AccessEndpoint {

    @POST("access/validateCustomer/{email}/{password}")
    fun validate(@Path("email") email: String, @Path("password") password: String): Observable<LoginDTO>

    @POST("access/googleSignin/{email}/{name}")
    fun googleSignin(@Path("email") email: String, @Path("name") name: String): Observable<LoginDTO>

    @GET("access/changePassword/{userId}/{password}")
    fun changePassword(@Path("userId") userId: String, @Path("password") password: String): Observable<String>
}