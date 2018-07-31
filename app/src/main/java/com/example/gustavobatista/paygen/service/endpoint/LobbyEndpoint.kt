package com.example.gustavobatista.paygen.service.endpoint

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LobbyEndpoint {

    @GET("lobby/checkIn/{userId}/{providerId}")
    fun checkin(@Path("userId") userId: String, @Path("providerId") providerID: String): Observable<String>
}