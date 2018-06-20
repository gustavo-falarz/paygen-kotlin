package com.example.gustavobatista.paygen.entity

import com.orm.SugarRecord

/**
 * Created by Gustavo on 10/23/2017.
 */
open class CreditCard:SugarRecord(){
    lateinit var cardNumber: String
    lateinit var cardHolder: String
    lateinit var securityCode: String
    lateinit var expiryMonth: String
    lateinit var expiryYear: String

}
