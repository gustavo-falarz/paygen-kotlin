package com.example.gustavobatista.paygen.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.activity.ProviderActivity
import com.example.gustavobatista.paygen.adapter.ProviderAdapter
import com.example.gustavobatista.paygen.entity.Provider
import com.example.gustavobatista.paygen.service.ProviderService
import com.example.gustavobatista.paygen.util.Constants
import kotlinx.android.synthetic.main.fragment_providers.*

class ProvidersFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_providers, container, false)
    }

    override fun onStart() {
        super.onStart()
        getProviders()
    }

    private fun getProviders() {
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

    private fun setAdapter(providers: List<Provider>) {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = ProviderAdapter(providers) {
            callActivity(it)
        }
        recyclerView.adapter = adapter
    }

    private fun callActivity(provider: Provider) {
        val intent = Intent(activity, ProviderActivity::class.java)
        intent.putExtra(Constants.TRANSITION_KEY_PROVIDER, provider)
        startActivity(intent)
    }
}
