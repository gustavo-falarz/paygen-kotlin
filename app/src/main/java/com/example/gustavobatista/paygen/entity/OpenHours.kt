package com.example.gustavobatista.paygen.entity

import java.io.Serializable

class OpenHours(var weekDay: String, var open: String, var closes: String) : Serializable {
    override fun toString(): String {
        return "$weekDay $open - $closes\n"
    }
}
