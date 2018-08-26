package com.example.gustavobatista.paygen.service

import com.example.gustavobatista.paygen.service.endpoint.LobbyEndpoint

object LobbyService {

    private val service: LobbyEndpoint
        get() = Service.createService(LobbyEndpoint::class.java)

    fun checkIn(userId: String, providerId: String) = service.checkIn(userId, providerId)

    fun checkOut(userId: String, providerId: String) = service.checkOut(userId, providerId)
}