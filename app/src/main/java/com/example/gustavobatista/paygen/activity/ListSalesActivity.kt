package com.example.gustavobatista.paygen.activity

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log


import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.entity.Customer
import com.example.gustavobatista.paygen.service.CustomerService

import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable


class ListSalesActivity : BaseActivity() {
    private var recyclerView: RecyclerView? = null
    private var layoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_sales)

        setupToolbar(R.string.title_customers)

        recyclerView = findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()


    }

    override fun onStart() {
        super.onStart()
        CustomerService.listAllCustomers().applySchedulers().subscribe(object : Observer<List<Customer>> {

            override fun onSubscribe(@NonNull d: Disposable) {

            }

            override fun onNext(@NonNull response: List<Customer>) {
                createAdapter(response)
            }

            override fun onError(@NonNull e: Throwable) {
                handleException(e)
            }

            override fun onComplete() {

            }
        })
    }

    fun createAdapter(response: List<Customer>) {

    }
}
