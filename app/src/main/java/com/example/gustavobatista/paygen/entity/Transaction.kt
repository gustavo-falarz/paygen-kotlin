package com.example.gustavobatista.paygen.entity

import java.io.Serializable

open class Transaction(var items: List<Item>,
                       var total: Double,
                       var customerId: String,
                       var providerId: String,
                       var id: String? = null) : Serializable {

    var payment: Payment? = null
    var date: String = ""
    var type: Transaction.Type = Type.LOCAL
    var discount: Double = 0.0

    enum class Type {
        DELIVERY,
        LOCAL
    }

    enum class Status {
        PAID,
        PENDING,
        CANCELED
    }


}
