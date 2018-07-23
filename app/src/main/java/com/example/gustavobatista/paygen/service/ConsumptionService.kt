package com.example.gustavobatista.paygen.service

import com.example.gustavobatista.paygen.entity.Consumption
import com.example.gustavobatista.paygen.entity.dto.ConsumptionDTO
import com.example.gustavobatista.paygen.service.endpoint.ConsumptionEndpoint
import io.reactivex.Observable

object ConsumptionService {
    private val service: ConsumptionEndpoint
        get() = Service.createService(ConsumptionEndpoint::class.java)

    fun getConsumption(dto: ConsumptionDTO): Observable<Consumption> = service.getConsumption(dto)
}