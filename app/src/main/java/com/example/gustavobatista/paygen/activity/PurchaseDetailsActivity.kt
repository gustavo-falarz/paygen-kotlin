package com.example.gustavobatista.paygen.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.adapter.ItemAdapter
import com.example.gustavobatista.paygen.entity.Transaction
import kotlinx.android.synthetic.main.activity_purchase_details.*
import com.example.gustavobatista.paygen.util.StringUtils.currency


class PurchaseDetailsActivity : BaseActivity() {
    lateinit var transaction: Transaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_details)
        setupActionBar()
        recyclerView.layoutManager = LinearLayoutManager(this)
        transaction = intent.getSerializableExtra("transaction") as Transaction
    }

    override fun onStart() {
        super.onStart()
        setData()
    }

    private fun setData() {
        val adapter = ItemAdapter(transaction.items)
        recyclerView.adapter = adapter

        tvId.text = transaction.id
        tvData.text = transaction.date
        tvDiscount.text = transaction.discount.currency()
        tvTotal.text = transaction.total.currency()
    }

}
