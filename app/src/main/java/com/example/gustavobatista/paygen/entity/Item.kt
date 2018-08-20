package com.example.gustavobatista.paygen.entity

import java.io.Serializable


open class Item : Serializable {

    var id: String? = null

    var description: String? = null

    var value: Double = 0.toDouble()

    var price: Double = 0.toDouble()

    var discount: Double = 0.toDouble()

}
