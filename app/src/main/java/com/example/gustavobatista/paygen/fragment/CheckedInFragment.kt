package com.example.gustavobatista.paygen.fragment


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.adapter.ItemAdapter
import com.example.gustavobatista.paygen.entity.Consumption
import com.example.gustavobatista.paygen.prefs
import com.example.gustavobatista.paygen.service.ConsumptionService
import kotlinx.android.synthetic.main.fragment_checked_in.*

class CheckedInFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_checked_in, container, false)
    }

    override fun onStart() {
        super.onStart()
        getConsumption()
    }

    private fun getConsumption() {
        showProgress()
        ConsumptionService.getConsumption(prefs.userId, prefs.providerId)
                .applySchedulers()
                .subscribe(
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

    private fun setAdapter(consumption: Consumption) {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = ItemAdapter(consumption.items)
        recyclerView.adapter = adapter
    }
}
