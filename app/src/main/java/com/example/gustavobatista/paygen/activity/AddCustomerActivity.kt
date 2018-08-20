package com.example.gustavobatista.paygen.activity

import android.os.Bundle
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.entity.Customer
import com.example.gustavobatista.paygen.service.CustomerService
import kotlinx.android.synthetic.main.activity_add_customer.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton


class AddCustomerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_customer)
        setupToolbar(R.string.title_new_customer)
        btCadastrar.setOnClickListener { checkFields() }
    }

    private fun checkFields() {
        if (etEmail.text.isNullOrEmpty() || etEmail.text.isNullOrEmpty()) {
            showWarning(R.string.warning_empty_fields)
            return
        }
        saveCustomer()
    }

    private fun saveCustomer() {
        val customer = Customer()
        customer.name = etName.text.toString()
        customer.email = etEmail.text.toString()
        showProgress()
        CustomerService.addUser(customer).applySchedulers().subscribe(
                {
                    handleResult(it)
                },
                {
                    closeProgress()
                    handleException(it)
                },
                {
                    closeProgress()
                })
    }

    private fun handleResult(message: String) {
        alert(message, getString(R.string.title_success)) {
            yesButton { finish() }
        }.show()
    }
}
