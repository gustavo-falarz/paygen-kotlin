package com.example.gustavobatista.paygen.activity

import android.os.Bundle
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.entity.Customer
import com.example.gustavobatista.paygen.service.CustomerService
import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
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
        if (etEmail.text.isNullOrEmpty()) {
            return
        }
        saveCustomer()
    }

    private fun saveCustomer() {
        val customer = Customer()
        customer.cpf = etEmail.text.toString()

        CustomerService.addUser(customer).applySchedulers()
                .subscribe(object : Observer<Customer> {
                    override fun onSubscribe(@NonNull d: Disposable) {
                        showProgress()
                    }

                    override fun onNext(@NonNull customer: Customer) {
                        closeProgress()
                        handleResult()
                    }

                    override fun onError(@NonNull e: Throwable) {
                        handleException(e)
                        closeProgress()
                    }

                    override fun onComplete() {
                        closeProgress()
                    }
                })
    }

    private fun handleResult() {
        alert(getString(R.string.message_account_activation), getString(R.string.title_success)) {
            yesButton { finish() }
        }
    }
}
