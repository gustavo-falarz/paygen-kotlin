package com.example.gustavobatista.paygen.activity

import android.app.Activity
import android.os.Bundle
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.prefs
import org.jetbrains.anko.startActivity

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (prefs.token == "") {
            startActivity<LoginActivity>()
        } else {
            startActivity<MainActivity>()
        }
    }
}
