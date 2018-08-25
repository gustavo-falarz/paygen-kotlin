package com.example.gustavobatista.paygen.service.endpoint

import com.example.gustavobatista.paygen.entity.Consumption
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ConsumptionEndpoint {

    @GET("consumption/getConsumption/{customerId}/{providerId}")
    fun getConsumption(
            @Path("customerId") customerId: String,
            @Path("providerId") providerId: String
    ): Observable<Consumption>

    @POST("consumption/addItem/{itemId}/{providerId}/{customerId}")
    fun addItem(@Path("itemId") itemId: String,
                @Path("providerId") providerId: String,
                @Path("customerId") customerId: String): Observable<String>
}