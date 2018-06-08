package com.example.gustavobatista.paygen.activity

import android.os.Bundle
import com.example.gustavobatista.paygen.R

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_customer)
        setupToolbar(R.string.title_new_customer)
    }
}
