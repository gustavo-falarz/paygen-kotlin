package com.example.gustavobatista.paygen.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.activity.ProviderActivity
import com.example.gustavobatista.paygen.adapter.ProviderAdapter
import com.example.gustavobatista.paygen.entity.Provider
import com.example.gustavobatista.paygen.entity.ProviderInfo
import com.example.gustavobatista.paygen.prefs
import com.example.gustavobatista.paygen.service.ProviderService
import com.example.gustavobatista.paygen.util.Constants
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_providers.*

class ProvidersFragment : BaseFragment() {
    lateinit var list: List<Provider>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_providers, container, false)
    }

    override fun onStart() {
        super.onStart()
        initViews()
        getProviders()
    }

    private fun initViews() {
        spTypes.adapter = ArrayAdapter<ProviderInfo.Type>(activity,
                android.R.layout.simple_spinner_dropdown_item, ProviderInfo.Type.values())
    }

    private fun onSelectType(): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                filterType(ProviderInfo.Type.values()[position])
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        }
    }

    private fun filterType(type: ProviderInfo.Type) {
        if (type != ProviderInfo.Type.ALL) {
            var newList = list.filter { it.info.type == type }
            setAdapter(newList)
        } else {
            setAdapter(list)
        }
    }

    private fun getProviders() {
        showProgress()
        val observable: Observable<List<Provider>> =
                if (prefs.latitude.isEmpty()) {
                    ProviderService.getProviders()
                } else {
                    ProviderService.findProvidersByLocation(prefs.latitude, prefs.longitude)
                }

        observable.applySchedulers().subscribe(
                {
                    list = it
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
        spTypes.onItemSelectedListener = onSelectType()
    }

    private fun callActivity(provider: Provider) {
        val intent = Intent(activity, ProviderActivity::class.java)
        intent.putExtra(Constants.TRANSITION_KEY_PROVIDER, provider)
        startActivity(intent)
    }
}
