package com.example.gustavobatista.paygen.activity

import android.os.Bundle
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.entity.CreditCard
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.activity_add_payment_method.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.yesButton
import com.example.gustavobatista.paygen.util.StringUtils.isValidCreditcard
import com.example.gustavobatista.paygen.util.FieldUtils.isEmpty


class AddPaymentMethodActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_payment_method)
        setupToolbar(R.string.title_add_credit_card)
        btCadastrar.setOnClickListener { onClickCadastrar() }
    }


    private fun onClickCadastrar() {
        when {
            emptyFields() -> showWarning(R.string.warning_empty_fields)

            invalidCreditCard() -> showWarning(getString(R.string.warning_invalid_card))

            invalidExpiryDate() -> showWarning(getString(R.string.warning_expired_card))

            invalidSecurityCode() -> showWarning(getString(R.string.warning_invalid_security_code))

            else -> saveCard()

        }
    }

    private fun saveCard() {
        doAsync {
            val creditCard = CreditCard()
            creditCard.cardNumber = etCardNumber.text.toString()
            creditCard.cardHolder = etCardHolder.text.toString()
            creditCard.expiryMonth = etExpiryMonth.text.toString()
            creditCard.expiryYear = etExpiryYear.text.toString()
            creditCard.securityCode = etSecurityCode.text.toString()
            SugarRecord.save(creditCard)
        }
        alert(getString(R.string.message_payment_method_added),
                getString(R.string.title_success)) { yesButton { finish() } }.show()

    }

    private fun emptyFields(): Boolean {
        return (etCardHolder.isEmpty() ||
                etExpiryMonth.isEmpty() ||
                etExpiryYear.isEmpty() ||
                etSecurityCode.isEmpty())
    }

    private fun invalidCreditCard(): Boolean {
        return etCardNumber.text.toString().isValidCreditcard()
    }

    private fun invalidExpiryDate(): Boolean {
        val month = etExpiryMonth.text.toString()
        val year = etExpiryYear.text.toString()
        return month.toInt() > 12 || month.length != 2 || year.length != 2
    }

    private fun invalidSecurityCode(): Boolean {
        return etSecurityCode.text.toString().length < 3
    }
}







