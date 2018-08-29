package com.example.gustavobatista.paygen.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.adapter.TransactionAdapter
import com.example.gustavobatista.paygen.entity.Transaction
import com.example.gustavobatista.paygen.entity.dto.DateFilter
import com.example.gustavobatista.paygen.prefs
import com.example.gustavobatista.paygen.service.TransactionService
import kotlinx.android.synthetic.main.activity_purchases.*
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*

class PurchasesActivity : BaseActivity() {

    var startLength = 0
    var endLength = 0
    private var locked = false
    private val sdf: SimpleDateFormat
        @SuppressLint("SimpleDateFormat")
        get() = SimpleDateFormat("dd/MM/yyyy")

    private val startWatcher: TextWatcher
        get() = object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {
                if (text != null) {
                    if (text.length > startLength) {

                        if (text.length == 2 || text.length == 5) {
                            text.append('/')
                        }
                    }
                    startLength = text.length
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        }
    private val endWatcher: TextWatcher
        get() = object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {
                if (text != null) {
                    if (text.length > endLength) {

                        if (text.length == 2 || text.length == 5) {
                            text.append('/')
                        }
                    }
                    endLength = text.length
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchases)
        setupActionBar()
        recyclerView.layoutManager = LinearLayoutManager(this)
        etEndDate.addTextChangedListener(endWatcher)
        etStartDate.addTextChangedListener(startWatcher)
        btOk.setOnClickListener { filterDate() }
        lFilter.setOnClickListener { lFilter.visibility = View.GONE }

    }

    override fun onStart() {
        super.onStart()
        getPurchases()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_one_day -> filterPurchases(1)
            R.id.action_seven_days -> filterPurchases(7)
            R.id.action_thirty_days -> filterPurchases(30)
            R.id.action_custom_date -> filterCustom()
            else -> super.onBackPressed()
        }
        return true
    }

    private fun filterCustom() {
        locked = true
        lFilter.visibility = View.VISIBLE
    }

    private fun filterDate() {
        var startDate: String = etStartDate.text.toString()
        var endDate: String = etEndDate.text.toString()

        if (startDate.isEmpty() || endDate.isEmpty()) {
            showWarning(getString(R.string.warning_empty_fields))
            return
        }
        if (startDate.length != 10 || endDate.length != 10) {
            showWarning(getString(R.string.warning_invalid_date))
            return
        }

        try {
            val calendar = Calendar.getInstance()


            val dateS = sdf.parse(startDate)

            val dateE = sdf.parse(endDate)
            calendar.time = dateE
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            val adjustedDateE = calendar.time

            startDate = sdf.format(dateS )
            endDate = sdf.format(adjustedDateE)

            if (dateS.after(dateE)) {
                showWarning(getString(R.string.warning_start_end_date))
                return
            }

        } catch (e: Exception) {
            showWarning(getString(R.string.warning_invalid_date))
            return
        }

        locked = false
        etEndDate.setText("")
        etStartDate.setText("")
        lFilter.visibility = View.GONE
        filterPurchases(startDate, endDate)
    }

    private fun filterPurchases(startDate: String, endDate: String) {
        showProgress()
        TransactionService.filterPurchases(prefs.userId, DateFilter(startDate, endDate)).applySchedulers().subscribe(
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

    private fun filterPurchases(daysAgo: Int) {
        showProgress()
        TransactionService.filterPurchases(prefs.userId, getFilter(daysAgo)).applySchedulers().subscribe(
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

    private fun getPurchases() {
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
            if (!locked) {
                startActivity<PurchaseDetailsActivity>("transaction" to it)
            }
        }
        recyclerView.adapter = adapter
    }

    private fun getFilter(daysAgo: Int): DateFilter {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val endDate = sdf.format(calendar.time)
        val startDate = sdf.format(getDaysAgo(daysAgo))
        return DateFilter(startDate, endDate)
    }

    private fun getDaysAgo(daysAgo: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)

        return calendar.time
    }
}