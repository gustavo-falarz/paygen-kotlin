package com.example.gustavobatista.paygen.service.endpoint


import com.example.gustavobatista.paygen.entity.Customer

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by Gustavo on 10/16/2017.
 */

interface CustomerEndpoint {

    @POST("customer/addCustomer")
    fun addUser(@Body customer: Customer): Observable<Customer>

    @GET("customer/findCustomer/{cpf}")
    fun findCustomer(@Path("cpf") cpf: String): Observable<Customer>

    @POST("customer/listAllCustomers")
    fun listAllCustomers(): Observable<List<Customer>>

}
