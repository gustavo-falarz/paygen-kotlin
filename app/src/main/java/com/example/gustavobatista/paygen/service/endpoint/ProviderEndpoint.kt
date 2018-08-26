package com.example.gustavobatista.paygen.service.endpoint

import com.example.gustavobatista.paygen.entity.Provider
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


interface ProviderEndpoint {

    @GET("provider")
    fun getProviders(): Observable<List<Provider>>

    @GET("provider/findProvidersByLocation/{latitude}/{longitude}")
    fun findProvidersByLocation(@Path("latitude") latitude: String,
                                @Path("longitude") longitude: String):
            Observable<List<Provider>>
}