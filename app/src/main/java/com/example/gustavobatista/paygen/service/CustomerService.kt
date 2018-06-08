package com.example.gustavobatista.paygen.service


import com.example.gustavobatista.paygen.entity.Customer
import com.example.gustavobatista.paygen.service.endpoint.CustomerEndpoint

import io.reactivex.Observable

/**
 * Created by Gustavo on 10/24/2017.
 */

object CustomerService : Service() {

    fun addUser(customer: Customer): Observable<Customer> = getInstance().addUser(customer)

    fun findCustomer(cpf: String): Observable<Customer> = getInstance().findCustomer(cpf)

    fun listAllCustomers(): Observable<List<Customer>> = getInstance().listAllCustomers()

    private fun getInstance(): CustomerEndpoint = Service.createService(CustomerEndpoint::class.java)

}
