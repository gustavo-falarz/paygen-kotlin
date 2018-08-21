package com.example.gustavobatista.paygen.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.adapter.AddItemAdapter
import com.example.gustavobatista.paygen.entity.Customer
import com.example.gustavobatista.paygen.entity.Item
import com.example.gustavobatista.paygen.prefs
import com.example.gustavobatista.paygen.service.ItemService
import kotlinx.android.synthetic.main.activity_add_items.*

class AddItemsActivity : BaseActivity() {

    private lateinit var customer: Customer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_items)
        setupActionBar()
        setupToolbar(R.string.title_add_items)
        customer = intent.getSerializableExtra("customer") as Customer

    }

    override fun onStart() {
        super.onStart()
        initView()
        listProducts()
    }

    private fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        btSearch.setOnClickListener { findProducts() }
    }

    private fun listProducts() {
        showProgress()
        ItemService.listProducts(prefs.providerId).applySchedulers().subscribe(
                {
                    createAdapter(it)
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

    private fun findProducts() {
        showProgress()
        val query = etQuery.text.toString()
        if (query.isEmpty()) {
            showWarning(getString(R.string.error_empty_query))
            return
        }
        ItemService.findItems(prefs.providerId, query).applySchedulers().subscribe(
                {
                    createAdapter(it)
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

    private fun addItem(item: Item) {

    }

    private fun createAdapter(list: List<Item>) {
        var adapter = AddItemAdapter(list) {
            addItem(it)
        }
        recyclerView.adapter = adapter
    }

}
