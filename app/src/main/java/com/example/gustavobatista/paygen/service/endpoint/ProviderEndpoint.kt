package com.example.gustavobatista.paygen.service.endpoint

import com.example.gustavobatista.paygen.entity.Provider
import io.reactivex.Observable
import retrofit2.http.GET


interface ProviderEndpoint {

    @GET("provider")
    fun getProviders(): Observable<List<Provider>>
}