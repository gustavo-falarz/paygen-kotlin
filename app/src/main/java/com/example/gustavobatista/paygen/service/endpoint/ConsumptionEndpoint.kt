package com.example.gustavobatista.paygen.service.endpoint

import com.example.gustavobatista.paygen.entity.Consumption
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


interface ConsumptionEndpoint {

    @GET("consumption/getConsumption/{customerId}/{providerId}")
    fun getConsumption(
            @Path("customerId") customerId: String,
            @Path("providerId") providerId: String
    ): Observable<Consumption>

}