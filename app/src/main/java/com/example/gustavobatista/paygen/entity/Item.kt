package com.example.gustavobatista.paygen.entity

import java.io.Serializable


open class Item : Serializable {

    var id: String = ""

    var name: String = ""

    var description: String = ""

    var value: Double = 0.toDouble()

    var price: Double = 0.toDouble()

    var discount: Double = 0.toDouble()

    var picture: String? = ""

}
