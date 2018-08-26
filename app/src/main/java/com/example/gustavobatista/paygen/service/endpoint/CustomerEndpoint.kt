package com.example.gustavobatista.paygen.service.endpoint


import com.example.gustavobatista.paygen.entity.Customer
import com.example.gustavobatista.paygen.entity.Provider
import com.example.gustavobatista.paygen.entity.dto.LoginDTO

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by Gustavo on 10/16/2017.
 */

interface CustomerEndpoint {

    @POST("access/addCustomer")
    fun addUser(@Body customer: Customer): Observable<String>

    @GET("customer/checkReception/{customerId}")
    fun checkReception(@Path("customerId") customerId: String): Observable<Provider>

    @POST("customer/updateProfile")
    fun updateProfile(@Body dto: LoginDTO): Observable<String>


}
