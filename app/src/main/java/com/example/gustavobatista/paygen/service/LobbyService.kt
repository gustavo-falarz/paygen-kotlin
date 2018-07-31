package com.example.gustavobatista.paygen.service

import com.example.gustavobatista.paygen.service.endpoint.LobbyEndpoint

object LobbyService {

    private val service: LobbyEndpoint
        get() = Service.createService(LobbyEndpoint::class.java)

    fun checkin(userId: String, providerId: String) = service.checkin(userId, providerId)
}