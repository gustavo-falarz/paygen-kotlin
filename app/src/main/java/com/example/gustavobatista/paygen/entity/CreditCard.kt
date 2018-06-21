package com.example.gustavobatista.paygen.entity

import com.orm.SugarRecord

/**
 * Created by Gustavo on 10/23/2017.
 */
open class CreditCard : SugarRecord() {
    lateinit var cardNumber: String
    lateinit var cardHolder: String
    lateinit var securityCode: String
    lateinit var expiryMonth: String
    lateinit var expiryYear: String
    lateinit var carrier: Carrier


    enum class Carrier(val regex: String, val logo: String) {
        VISA("^4[0-9]{12}(?:[0-9]{3})?$", "https://resources.mynewsdesk.com/image/upload/t_next_gen_article_large_480/ojf8ed4taaxccncp6pcp.jpg"),
        MASTERCARD("^5[1-5][0-9]{14}$", "https://cdn.iconscout.com/public/images/icon/free/png-256/mastercard-logo-3485dff060e06bba-256x256.png"),
        AMEX("^3[47][0-9]{13}$", "https://cdn.icon-icons.com/icons2/1178/PNG/512/amex-inverted_82041.png"),
        DINERS("^3(?:0[0-5]|[68][0-9])[0-9]{11}$", ""),
        DISCOVER("^6(?:011|5[0-9]{2})[0-9]{12}$", ""),
        JCB("^(?:2131|1800|35\\d{3})\\d{11}$", ""),
        INVALID("", "")

    }

}
