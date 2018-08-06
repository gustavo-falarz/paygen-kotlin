package com.example.gustavobatista.paygen.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.entity.CreditCard
import com.example.gustavobatista.paygen.entity.Payment
import com.example.gustavobatista.paygen.entity.Transaction
import com.example.gustavobatista.paygen.service.TransactionService
import com.example.gustavobatista.paygen.util.Constants
import com.example.gustavobatista.paygen.util.SaleUtils
import com.example.gustavobatista.paygen.util.StringUtils.currency
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.activity_payment.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity


@Suppress("UNCHECKED_CAST")
class PaymentActivity : BaseActivity() {

    lateinit var transaction: Transaction
    lateinit var creditCard: CreditCard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        transaction = intent.getSerializableExtra(Constants.TRANSITION_KEY_TRANSACTION) as Transaction

        btPay.setOnClickListener {onClickPay()}
    }

    override fun onStart() {
        super.onStart()
        populate()
    }

    private fun populate() {
        tvTotal.text = transaction.total.currency()

        val adapter = ArrayAdapter<CreditCard>(
                this, android.R.layout.simple_spinner_item, getCards())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spCreditCards.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                creditCard = getCards()[position]
            }

        }
        spCreditCards.adapter = adapter
    }

    private fun getCards(): List<CreditCard> {
        val cards = SugarRecord.listAll(CreditCard::class.java)
        creditCard = cards[0]
        if (cards.isEmpty()) {
            alert(getString(R.string.message_no_card_registered), getString(R.string.title_delete)) {
                positiveButton(R.string.ok) { startActivity<AddPaymentMethodActivity>() }
            }.show()
        }
        return cards
    }

    private fun onClickPay() {
        var payment = Payment()
        payment.amount = SaleUtils.getTotalInCents(transaction.items)
        payment.installments = 1
        payment.type = "CREDITCARD"
        payment.creditCard = creditCard

        saveTransaction()
    }

    private fun saveTransaction() {
        showProgress()

        TransactionService.addTransaction(transaction).applySchedulers().subscribe(
                {
                    handleTransaction(it)
                },
                {
                    handleException(it)
                    closeProgress()
                },
                {
                    closeProgress()
                }
        )
    }

    fun handleTransaction(transaction: Transaction) {

    }

}
