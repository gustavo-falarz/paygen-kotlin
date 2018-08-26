package com.example.gustavobatista.paygen.service


import com.example.gustavobatista.paygen.entity.Customer
import com.example.gustavobatista.paygen.entity.dto.LoginDTO
import com.example.gustavobatista.paygen.service.endpoint.CustomerEndpoint

/**
 * Created by Gustavo on 10/24/2017.
 */

object CustomerService : Service() {

    fun addUser(customer: Customer) = service.addUser(customer)

    fun checkReception(customerId: String) = service.checkReception(customerId)

    fun updateProfile(dto: LoginDTO) = service.updateProfile(dto)

    private val service: CustomerEndpoint
        get() = Service.createService(CustomerEndpoint::class.java)

}
