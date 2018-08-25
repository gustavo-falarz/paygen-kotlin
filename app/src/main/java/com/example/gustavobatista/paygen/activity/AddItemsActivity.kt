package com.example.gustavobatista.paygen.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.adapter.ItemMenuAdapter
import com.example.gustavobatista.paygen.entity.Item
import com.example.gustavobatista.paygen.prefs
import com.example.gustavobatista.paygen.service.ConsumptionService
import com.example.gustavobatista.paygen.service.ItemService
import kotlinx.android.synthetic.main.activity_add_items.*
import org.jetbrains.anko.alert

class AddItemsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_items)
        setupActionBar()
        setupToolbar(R.string.title_add_items)
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
        ItemService.findProducts(prefs.providerId, query).applySchedulers().subscribe(
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
        showProgress()
        ConsumptionService.addItem(prefs.providerId, prefs.userId, item.id).applySchedulers().subscribe(
                {
                    showMessage(it)
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

    private fun createAdapter(list: List<Item>) {
        val adapter = ItemMenuAdapter(list,
                {
                    addItem(it)
                }
        ) { name, desc ->
            alert(name, desc) { }
        }

        recyclerView.adapter = adapter
    }

}
