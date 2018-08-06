package com.example.gustavobatista.paygen.entity

import java.io.Serializable

open class Transaction(var items: List<Item>,
                       var total: Double,
                       var paymentMethod: PaymentMethod,
                       var customerId: String,
                       var providerId: String,
                       var id: String? = null) : Serializable {


    enum class PaymentMethod {
        CREDIT_CARD,
        DEBIT,
        CASH

    }

    enum class Status {
        PAID,
        PENDING,
        CANCELED
    }


}
