package com.example.gustavobatista.paygen.service.endpoint

import com.example.gustavobatista.paygen.entity.Transaction
import com.example.gustavobatista.paygen.entity.dto.DateFilter
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TransactionEndpoint {

    @POST("transaction/addTransaction")
    fun addTransaction(@Body transaction: Transaction): Observable<String>

    @GET("transaction/getCustomerTransactions/{customerId}")
    fun getTransactions(@Path("customerId") customerId: String): Observable<List<Transaction>>

    @POST("transaction/filterPurchases/{userId}")
    fun filterPurchases(@Path("userId") userId: String,
                    @Body filter: DateFilter): Observable<List<Transaction>>
}