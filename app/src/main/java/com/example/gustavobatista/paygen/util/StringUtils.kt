package com.example.gustavobatista.paygen.util

import com.example.gustavobatista.paygen.entity.CreditCard
import java.text.NumberFormat
import java.util.*

object StringUtils {

    fun String.maskNumber(): String {
        return this.replaceRange(kotlin.ranges.IntRange(0, 11), "**** ")
    }

    fun String.isValidCreditcard(): Boolean {
        val digits = this.length
        val oddOrEven = digits and 1
        var sum: Long = 0
        for (count in 0 until digits) {
            var digit: Int
            try {
                digit = Integer.parseInt(this[count] + "")
            } catch (e: NumberFormatException) {
                return false
            }
            if (count and 1 xor oddOrEven == 0) { // not
                digit *= 2
                if (digit > 9) {
                    digit -= 9
                }
            }
            sum += digit.toLong()
        }
        return if (sum == 0L) false else sum % 10 == 0L
    }

    fun getCarrier(cardNumber: String): CreditCard.Carrier {
        return when {
            CreditCard.Carrier.AMEX.regex.toRegex().matches(cardNumber) -> CreditCard.Carrier.AMEX
            CreditCard.Carrier.MASTER.regex.toRegex().matches(cardNumber) -> CreditCard.Carrier.MASTER
            CreditCard.Carrier.VISA.regex.toRegex().matches(cardNumber) -> CreditCard.Carrier.VISA
            CreditCard.Carrier.DINERS.regex.toRegex().matches(cardNumber) -> CreditCard.Carrier.DINERS
            CreditCard.Carrier.DISCOVER.regex.toRegex().matches(cardNumber) -> CreditCard.Carrier.DISCOVER
            else -> CreditCard.Carrier.INVALID
        }
    }


    fun String.currency(): String {
        var doubleValue: Double = 0.0
        if (this != null) doubleValue = this.toDouble()

        return doubleValue.currency()
    }

    fun Double.currency(): String {
        return NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(this)
    }

}