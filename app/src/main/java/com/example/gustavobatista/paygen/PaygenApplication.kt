package com.example.gustavobatista.paygen

import com.example.gustavobatista.paygen.util.UserInfo
import com.orm.SugarApp

val prefs: UserInfo by lazy {
    PaygenApplication.prefs
}

class PaygenApplication : SugarApp() {

    companion object {
        lateinit var prefs: UserInfo
    }

    override fun onCreate() {
        super.onCreate()
        prefs = UserInfo(applicationContext)

    }
}