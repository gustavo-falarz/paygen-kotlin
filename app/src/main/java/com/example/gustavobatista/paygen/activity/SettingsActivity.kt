package com.example.gustavobatista.paygen.activity

import android.os.Bundle
import com.example.gustavobatista.paygen.R
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.startActivity

class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setupActionBar()

        btChangePassword.setOnClickListener { onClickChangePassword() }
    }

    private fun onClickChangePassword() {
        startActivity<ChangePasswordActivity>()
    }
}
