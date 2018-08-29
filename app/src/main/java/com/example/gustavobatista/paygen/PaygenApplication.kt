package com.example.gustavobatista.paygen

import android.content.Context
import com.example.gustavobatista.paygen.util.UserInfo
import com.orm.SugarApp

val prefs: UserInfo by lazy {
    PaygenApplication.prefs
}

class PaygenApplication : SugarApp() {

    companion object {
        lateinit var prefs: UserInfo
        private var instance: PaygenApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        prefs = UserInfo(applicationContext)

    }
}