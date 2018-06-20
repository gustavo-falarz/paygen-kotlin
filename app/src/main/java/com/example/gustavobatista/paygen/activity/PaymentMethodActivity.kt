package com.example.gustavobatista.paygen.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.adapter.PaymentMethodAdapter
import com.example.gustavobatista.paygen.entity.CreditCard
import com.orm.SugarRecord
import kotlinx.android.synthetic.main.activity_payment_methods.*
import org.jetbrains.anko.startActivity

class PaymentMethodActivity : BaseActivity() {

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
        val adapter = PaymentMethodAdapter(paymentMethods) {}
        recyclerView.adapter = adapter
    }

}
