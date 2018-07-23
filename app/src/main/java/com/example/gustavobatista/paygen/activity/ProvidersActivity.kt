package com.example.gustavobatista.paygen.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.adapter.ProviderAdapter
import com.example.gustavobatista.paygen.entity.Provider
import com.example.gustavobatista.paygen.service.ProviderService
import kotlinx.android.synthetic.main.activity_providers.*

class ProvidersActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_providers)
        recyclerView.layoutManager = LinearLayoutManager(this)
        getProviders()
    }

    fun getProviders() {
        showProgress()
        ProviderService.getProviders().applySchedulers().subscribe(
                {
                    setAdapter(it)
                    closeProgress()
                },
                {
                    handleException(it)
                    closeProgress()
                }
        )
    }

    fun setAdapter(providers: List<Provider>) {
        val adapter = ProviderAdapter(providers)
        recyclerView.adapter = adapter
    }
}