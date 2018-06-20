package com.example.gustavobatista.paygen.activity

import android.os.Bundle
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.entity.CreditCard
import kotlinx.android.synthetic.main.activity_add_payment_method.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.yesButton


class AddPaymentMethodActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_payment_method)
        setupToolbar(R.string.title_add_credit_card)
        btCadastrar.setOnClickListener { onClickCadastrar() }
    }


    private fun onClickCadastrar() {
        val cardNumber = etCardNumber.text.toString()
        val cardHolder = etCardHolder.text.toString()
        val expiryMonth = etExpiryMonth.text.toString()
        val expiryYear = etExpiryYear.text.toString()
        val securityCode = etSecurityCode.text.toString()
        when {
            cardNumber.isBlank() ||
                    cardHolder.isBlank() ||
                    expiryMonth.isBlank() ||
                    expiryYear.isBlank() ||
                    securityCode.isBlank() -> showWarning(R.string.warning_empty_fields)
            else -> {
                val creditCard = CreditCard()
                creditCard.cardNumber = cardNumber
                creditCard.cardHolder = cardHolder
                creditCard.expiryMonth = expiryMonth
                creditCard.expiryYear = expiryYear
                creditCard.securityCode = securityCode
                saveCard(creditCard)
            }
        }
    }

    private fun saveCard(creditCard: CreditCard) {
        doAsync {
            creditCard.save()
        }
        alert(getString(R.string.message_payment_method_added),
                getString(R.string.title_success)) { yesButton { finish() } }.show()

    }
}







