package com.example.gustavobatista.paygen.service

import com.example.gustavobatista.paygen.service.endpoint.AccessEndpoint

object AccessService : Service() {
    val service: AccessEndpoint
        get() = createService(AccessEndpoint::class.java)

    fun validate(email: String, password: String) = service.validate(email, password)

    fun googleSignin(email: String, name: String) = service.googleSignin(email, name)

    fun changePassword(userId: String, password: String) = service.changePassword(userId, password)
}