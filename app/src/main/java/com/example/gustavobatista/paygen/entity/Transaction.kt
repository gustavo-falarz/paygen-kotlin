package com.example.gustavobatista.paygen.entity

import java.io.Serializable

open class Transaction:Serializable {

    var purchaseId: String? = null

    var items: List<Item>? = null

    var total: Double = 0.toDouble()

    var discount: Double = 0.toDouble()

    var paymentMethod: PaymentMethod? = null

    enum class PaymentMethod {
        CREDIT_CARD,
        DEBIT,
        CASH

    }
    enum class Status{
        PAID,
        PENDING,
        CANCELED
    }


}
