package com.example.gustavobatista.paygen.service

import com.example.gustavobatista.paygen.service.endpoint.ItemsEndpoint


object ItemService : Service() {
    private val service: ItemsEndpoint
        get() = createService(ItemsEndpoint::class.java)

    fun listProducts(providerId: String) = service.listProducts(providerId)

    fun findItems(providerId: String, query: String) = service.findProduct(providerId, query)
}