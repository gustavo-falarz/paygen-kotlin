package com.example.gustavobatista.paygen.service.endpoint

import com.example.gustavobatista.paygen.entity.CieloTransaction
import com.example.gustavobatista.paygen.util.Constants
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface CieloEndpoint {

    @Headers(
            "MerchantId: ${Constants.MERCHANT_ID}",
            "MerchantKey: ${Constants.MERCHANT_KEY}")
    @POST("1/sales")
    fun paySimple(@Body transactionEndpoint: CieloTransaction): Observable<CieloTransaction>
}