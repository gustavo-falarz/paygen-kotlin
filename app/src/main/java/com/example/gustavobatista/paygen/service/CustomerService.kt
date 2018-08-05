package com.example.gustavobatista.paygen.service


import com.example.gustavobatista.paygen.entity.Customer
import com.example.gustavobatista.paygen.service.endpoint.CustomerEndpoint

import io.reactivex.Observable

/**
 * Created by Gustavo on 10/24/2017.
 */

object CustomerService : Service() {

    fun addUser(customer: Customer): Observable<Customer> = service.addUser(customer)

    fun findCustomer(cpf: String): Observable<Customer> = service.findCustomer(cpf)

    fun listAllCustomers(): Observable<List<Customer>> = service.listAllCustomers()

    fun checkReception(customerId: String): Observable<Boolean> = service.checkReception(customerId)


    private val service: CustomerEndpoint
        get() = Service.createService(CustomerEndpoint::class.java)

}
