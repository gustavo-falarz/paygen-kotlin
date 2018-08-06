package com.example.gustavobatista.paygen.entity

class Payment {

    var amount: Int = 0

    var returnCode: String? = null

    var serviceTaxAmount: String? = null

    var type: String? = null

    var receivedDate: String? = null

    var country: String? = null

    var tid: String? = null

    var returnMessage: String? = null

    var authenticate: String? = null

    var creditCard: CreditCard? = null

    var proofOfSale: String? = null

    var recurrent: String? = null

    var status: String? = null

    var isSplitted: String? = null

    var links: Array<Links>? = null

    var capture: String? = null

    var authorizationCode: String? = null

    var interest: String? = null

    var softDescriptor: String? = null

    var installments: Int = 0

    var currency: String? = null

    var provider: String? = null

    var paymentId: String? = null

}
