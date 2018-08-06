package com.example.gustavobatista.paygen.service.endpoint

import com.example.gustavobatista.paygen.entity.Transaction
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface TransactionEndpoint {

    @POST("transaction/addTransaction")
    fun addTransaction(@Body transaction: Transaction): Observable<Transaction>

}