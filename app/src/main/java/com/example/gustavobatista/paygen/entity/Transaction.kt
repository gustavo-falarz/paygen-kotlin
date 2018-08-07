package com.example.gustavobatista.paygen.entity

import java.io.Serializable

open class Transaction(var items: List<Item>,
                       var total: Double,
                       var customerId: String,
                       var providerId: String,
                       var id: String? = null) : Serializable {

    var payment: Payment? = null


    enum class Status {
        PAID,
        PENDING,
        CANCELED
    }


}
