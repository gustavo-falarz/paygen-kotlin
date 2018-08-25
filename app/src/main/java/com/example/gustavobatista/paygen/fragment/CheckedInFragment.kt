package com.example.gustavobatista.paygen.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.activity.AddItemsActivity
import com.example.gustavobatista.paygen.activity.PaymentActivity
import com.example.gustavobatista.paygen.adapter.ItemAdapter
import com.example.gustavobatista.paygen.entity.Consumption
import com.example.gustavobatista.paygen.entity.Item
import com.example.gustavobatista.paygen.entity.Transaction
import com.example.gustavobatista.paygen.entity.dataclass.ProviderDataClass
import com.example.gustavobatista.paygen.prefs
import com.example.gustavobatista.paygen.service.ConsumptionService
import com.example.gustavobatista.paygen.util.Constants
import com.example.gustavobatista.paygen.util.ImageUtil.load
import com.example.gustavobatista.paygen.util.SaleUtils
import com.example.gustavobatista.paygen.util.StringUtils.currency
import kotlinx.android.synthetic.main.fragment_checked_in.*
import org.jetbrains.anko.startActivity

class CheckedInFragment : BaseFragment() {
    private lateinit var items: List<Item>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_checked_in, container, false)
    }

    private fun initViews() {
        imLogo.load(ProviderDataClass.provider!!.info.logo) { request -> request.resize(200, 200).centerInside() }
        tvProviderName.text = ProviderDataClass.provider!!.name
        swipeRefresh.setOnRefreshListener { getConsumption() }
        fabPay.setOnClickListener { onClickPay() }
        fabAddItem.setOnClickListener { addItem() }
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun addItem() {
        fabMenu.hideMenu(true)
        startActivity<AddItemsActivity>()
    }

    private fun onClickPay() {
        fabMenu.hideMenu(true)
        val transaction = Transaction(items,
                SaleUtils.getTotalCost(items),
                prefs.userId, prefs.providerId)
        transaction.discount = SaleUtils.getTotalDiscount(items)

        val intent = Intent(activity, PaymentActivity::class.java)
        intent.putExtra(Constants.TRANSITION_KEY_TRANSACTION, transaction)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        initViews()
        getConsumption()
    }

    private fun getConsumption() {
        swipeRefresh.isRefreshing = false
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
        items = consumption.items
        tvTotal.text = SaleUtils.getTotalCost(consumption.items).currency()
        val adapter = ItemAdapter(consumption.items)
        recyclerView.adapter = adapter
    }
}
