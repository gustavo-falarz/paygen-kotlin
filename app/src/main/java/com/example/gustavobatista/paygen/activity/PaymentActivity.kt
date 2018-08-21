package com.example.gustavobatista.paygen.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.entity.*
import com.example.gustavobatista.paygen.service.PaymentService
import com.example.gustavobatista.paygen.service.TransactionService
import com.example.gustavobatista.paygen.util.Constants
import com.example.gustavobatista.paygen.util.SaleUtils.getTotalInCents
import com.example.gustavobatista.paygen.util.StringUtils.currency
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.activity_payment.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity


@Suppress("UNCHECKED_CAST")
class PaymentActivity : BaseActivity() {

    private lateinit var transaction: Transaction
    private lateinit var creditCard: CreditCard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        transaction = intent.getSerializableExtra(Constants.TRANSITION_KEY_TRANSACTION) as Transaction
        setupToolbar(R.string.title_payment)
        setupActionBar()
        btPay.setOnClickListener { onClickPay() }
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
        spCreditCards.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
        when {
            cards.isEmpty() -> alert(getString(R.string.message_no_card_registered),
                    getString(R.string.error_title)) {
                positiveButton(R.string.ok) { startActivity<AddPaymentMethodActivity>() }
            }.show()
            else -> creditCard = cards[0]
        }
        return cards
    }

    private fun onClickPay() {
        val transactionCielo = CieloTransaction()
        val payment = Payment()
        payment.amount = getTotalInCents(transaction.items)
        payment.installments = 1
        payment.type = "CREDITCARD"
        payment.creditCard = creditCard
        val customer = Customer()
        customer.name = creditCard.cardHolder
        transactionCielo.customer = customer
        transactionCielo.merchantOrderId = java.util.UUID.randomUUID().toString()
        transactionCielo.payment = payment
        sendPayment(transactionCielo)
    }

    private fun sendPayment(transactionCielo: CieloTransaction) {
        showProgress()
        PaymentService.paySimple(transactionCielo).applySchedulers().subscribe(
                {
                    handleCieloTransaction(it)
                },
                {
                    closeProgress()
                    handleException(it)
                })
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

    private fun handleCieloTransaction(cieloTransaction: CieloTransaction) {
        transaction.payment = cieloTransaction.payment
        saveTransaction()
    }

    private fun handleTransaction(transaction: String) {
        alert(transaction, getString(R.string.title_success)) {
            positiveButton(R.string.ok) {
                startActivity<MainActivity>()
                val intent = Intent(getActivity(), MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.show()
    }

}
