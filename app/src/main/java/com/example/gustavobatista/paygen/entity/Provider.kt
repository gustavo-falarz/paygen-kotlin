package com.example.gustavobatista.paygen.entity


/**
 * Created by Headtrap on 28/08/2017.
 */

class Provider(val type: Type,
               val lobby: Lobby,
               val location: Point,
               val sales: List<Transaction>,
               val consumptions: List<Consumption>,
               val employees: List<User>,
               val info: ProviderInfo) : User(){


    enum class Type {
        RESTAURANT,
        HAMBURGUER,
        PIZZA
    }
}
