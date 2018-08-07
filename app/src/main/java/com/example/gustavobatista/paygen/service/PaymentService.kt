package com.example.gustavobatista.paygen.service

import com.example.gustavobatista.paygen.entity.CieloTransaction
import com.example.gustavobatista.paygen.service.endpoint.CieloEndpoint

object PaymentService : ServiceCielo() {

    private val service: CieloEndpoint
        get() = createService(CieloEndpoint::class.java)

    fun paySimple(transaction: CieloTransaction) = service.paySimple(transaction)

}