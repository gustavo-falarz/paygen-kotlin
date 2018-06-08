package com.example.gustavobatista.paygen.entity

open class Transaction {

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


}
