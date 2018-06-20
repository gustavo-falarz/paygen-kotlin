package com.example.gustavobatista.paygen.util

object StringUtils {

    fun String.maskNumber(): String {
        return this
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

    fun getCarrier(cardNumber: String):Carrier{
        return when{
            Carrier.AMEX.regex.toRegex().matches(cardNumber) -> Carrier.AMEX
            Carrier.MASTERCARD.regex.toRegex().matches(cardNumber) -> Carrier.MASTERCARD
            Carrier.VISA.regex.toRegex().matches(cardNumber) -> Carrier.VISA
            Carrier.DINERS.regex.toRegex().matches(cardNumber) -> Carrier.DINERS
            Carrier.DISCOVER.regex.toRegex().matches(cardNumber) -> Carrier.DISCOVER
            else -> Carrier.INVALID
        }
    }

    enum class Carrier(val regex: String) {
        VISA ("^4[0-9]{12}(?:[0-9]{3})?$"),
        MASTERCARD ("^5[1-5][0-9]{14}$"),
        AMEX ("^3[47][0-9]{13}$"),
        DINERS ("^3(?:0[0-5]|[68][0-9])[0-9]{11}$"),
        DISCOVER ("^6(?:011|5[0-9]{2})[0-9]{12}$"),
        JCB ("^(?:2131|1800|35\\d{3})\\d{11}$"),
        INVALID ("")

    }


}