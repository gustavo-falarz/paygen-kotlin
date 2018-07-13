package com.example.gustavobatista.paygen.activity

import android.os.Bundle
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.adapter.ItemAdapter
import com.example.gustavobatista.paygen.adapter.ProviderAdapter
import com.example.gustavobatista.paygen.entity.Consumption
import com.example.gustavobatista.paygen.entity.dto.ConsumptionDTO
import com.example.gustavobatista.paygen.service.ConsumptionService
import kotlinx.android.synthetic.main.activity_providers.*

class ConsumptionActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumption)
        getConsumption()
    }

    private fun getConsumption() {
        showProgress()
        ConsumptionService.getConsumption(ConsumptionDTO()).applySchedulers().subscribe(
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
        val adapter = ItemAdapter(consumption.items)
        recyclerView.adapter = adapter
    }
}
