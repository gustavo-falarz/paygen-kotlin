package com.example.gustavobatista.paygen.activity

import android.os.Bundle
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.entity.Customer
import com.example.gustavobatista.paygen.service.CustomerService
import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_add_customer.*


class AddCustomerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_customer)
        setupToolbar(R.string.title_new_customer)
    }

    fun checkFields() {
        if (etEmail.text.isNullOrEmpty()) {
            return
        }
        saveCustomer()

    }

    fun saveCustomer() {
        val customer = Customer()
        customer.cpf = etEmail.text.toString()

        CustomerService.addUser(customer).applySchedulers()
                .subscribe(object : Observer<Customer> {
                    override fun onSubscribe(@NonNull d: Disposable) {
                        showProgress()
                    }

                    override fun onNext(@NonNull customer: Customer) {
                        finish()
                        closeProgress()
                    }

                    override fun onError(@NonNull e: Throwable) {

                    }

                    override fun onComplete() {
                        closeProgress()
                    }
                })
    }

}
