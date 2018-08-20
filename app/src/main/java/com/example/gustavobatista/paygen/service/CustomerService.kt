package com.example.gustavobatista.paygen.service


import com.example.gustavobatista.paygen.entity.Customer
import com.example.gustavobatista.paygen.service.endpoint.CustomerEndpoint

/**
 * Created by Gustavo on 10/24/2017.
 */

object CustomerService : Service() {

    fun addUser(customer: Customer) = service.addUser(customer)

    fun findCustomer(cpf: String) = service.findCustomer(cpf)

    fun listAllCustomers() = service.listAllCustomers()

    fun checkReception(customerId: String) = service.checkReception(customerId)


    private val service: CustomerEndpoint
        get() = Service.createService(CustomerEndpoint::class.java)

}
