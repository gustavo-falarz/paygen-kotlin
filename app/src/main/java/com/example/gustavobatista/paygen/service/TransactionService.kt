package com.example.gustavobatista.paygen.service

import com.example.gustavobatista.paygen.entity.Transaction
import com.example.gustavobatista.paygen.entity.dto.DateFilter
import com.example.gustavobatista.paygen.service.endpoint.TransactionEndpoint

object TransactionService {

    private val service: TransactionEndpoint
        get() = Service.createService(TransactionEndpoint::class.java)

    fun addTransaction(transaction: Transaction) = service.addTransaction(transaction)

    fun getTransactions(userId: String) = service.getTransactions(userId)

    fun filterPurchases(userId: String, filter: DateFilter) = service.filterPurchases(userId, filter)

}