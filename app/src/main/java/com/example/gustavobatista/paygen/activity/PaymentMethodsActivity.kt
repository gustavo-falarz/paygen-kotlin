package com.example.gustavobatista.paygen.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.adapter.PaymentMethodAdapter
import com.example.gustavobatista.paygen.entity.CreditCard
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.activity_payment_methods.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity

class PaymentMethodsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_methods)
        setupToolbar(R.string.title_payment_methods)
        setupActionBar()
        recyclerView.layoutManager = LinearLayoutManager(this)
        btAdd.setOnClickListener { startActivity<AddPaymentMethodActivity>() }
    }

    override fun onStart() {
        super.onStart()
        initRecycler()
    }

    private fun initRecycler() {
        val paymentMethods = SugarRecord.listAll(CreditCard::class.java)
        val adapter = PaymentMethodAdapter(paymentMethods) { checkDelete(it) }
        recyclerView.adapter = adapter
    }

    private fun checkDelete(creditCard: CreditCard) {
        alert(getString(R.string.message_delete), getString(R.string.title_delete)) {
            positiveButton(R.string.yes) { deleteMethod(creditCard) }
            negativeButton(R.string.no) { }
        }.show()
    }

    private fun deleteMethod(creditCard: CreditCard) {
        SugarRecord.delete(creditCard)
        initRecycler()
    }
}
