package com.example.gustavobatista.paygen.entity

import java.io.Serializable

class ProviderInfo(var banner: String, var address: String, var about: String, var type: Type, var openHours: List<OpenHours>) : Serializable {

    enum class Type {
        RESTAURANT,
        HAMBURGUER,
        PIZZA
    }
}
