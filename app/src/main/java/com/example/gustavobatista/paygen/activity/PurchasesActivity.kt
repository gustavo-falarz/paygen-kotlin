package com.example.gustavobatista.paygen.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.adapter.TransactionAdapter
import com.example.gustavobatista.paygen.entity.Transaction
import com.example.gustavobatista.paygen.prefs
import com.example.gustavobatista.paygen.service.TransactionService
import kotlinx.android.synthetic.main.activity_sales.*
import org.jetbrains.anko.startActivity

class PurchasesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales)
        setupActionBar()
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        getSales()
    }

    private fun getSales() {
        showProgress()
        TransactionService.getTransactions(prefs.userId).applySchedulers().subscribe(
                {
                    setupAdapter(it)
                },
                {
                    closeProgress()
                    handleException(it)
                },
                {
                    closeProgress()
                }
        )
    }

    private fun setupAdapter(transactions: List<Transaction>) {
        val adapter = TransactionAdapter(transactions) {
            startActivity<PurchaseDetailsActivity>("transaction" to it)
        }
        recyclerView.adapter = adapter
    }
}
