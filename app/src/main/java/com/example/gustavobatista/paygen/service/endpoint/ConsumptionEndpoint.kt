package com.example.gustavobatista.paygen.service.endpoint

import com.example.gustavobatista.paygen.entity.Consumption
import com.example.gustavobatista.paygen.entity.Item
import com.example.gustavobatista.paygen.entity.dto.ConsumptionDTO
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET


interface ConsumptionEndpoint {

    @GET("consumption")
    fun getConsumption(@Body dto: ConsumptionDTO): Observable<Consumption>
}