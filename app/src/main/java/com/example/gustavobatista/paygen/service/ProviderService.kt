package com.example.gustavobatista.paygen.service

import com.example.gustavobatista.paygen.entity.Provider
import com.example.gustavobatista.paygen.service.endpoint.ProviderEndpoint
import io.reactivex.Observable

object ProviderService {
    private val service: ProviderEndpoint
        get() = Service.createService(ProviderEndpoint::class.java)

    fun getProviders(): Observable<List<Provider>> = service.getProviders()

    fun findProvidersByLocation(latitude: String, longitude: String): Observable<List<Provider>> =
            service.findProvidersByLocation(latitude, longitude)
}
