package com.example.gustavobatista.paygen.util

import com.example.gustavobatista.paygen.entity.Item

object SaleUtils {

    fun getTotalInCents(items: List<Item>): Int {
        var total = 0.0

        items.forEach { total += it.getFinalValue() }

        return realToCents(total).toInt()
    }
    fun getTotalCost (items: List<Item>): Double {
        var total = 0.0

        items.forEach { total += it.getFinalValue() }

        return total
    }

    fun getTotalDiscount(items: List<Item>): Double {
        var total = 0.0

        items.forEach { total += it.getFinalValue() }

        return total
    }

    private fun realToCents(value: Double) = value * 100

    fun Item.getFinalValue() = this.price - discount
}
