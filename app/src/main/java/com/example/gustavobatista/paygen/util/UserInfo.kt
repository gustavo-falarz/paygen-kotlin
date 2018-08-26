package com.example.gustavobatista.paygen.util

import android.content.Context
import android.content.SharedPreferences
import com.example.gustavobatista.paygen.PaygenApplication.Companion.prefs
import com.example.gustavobatista.paygen.util.Constants.PREF_GOOGLE
import com.example.gustavobatista.paygen.util.Constants.PREF_ID
import com.example.gustavobatista.paygen.util.Constants.PREF_KEY
import com.example.gustavobatista.paygen.util.Constants.PREF_PICTURE
import com.example.gustavobatista.paygen.util.Constants.PREF_PROVIDER_ID
import com.example.gustavobatista.paygen.util.Constants.PREF_TOKEN
import com.example.gustavobatista.paygen.util.Constants.PREF_USERNAME
import com.firebase.ui.auth.AuthUI

/**
 * Created by Gustavo on 12/26/2017.
 */
class UserInfo(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_KEY, 0)

    companion object {
        fun clearData(context: Context) {
            AuthUI.getInstance()
                    .signOut(context)
                    .addOnCompleteListener {
                    }
            prefs.token = ""
            prefs.userId = ""
            prefs.userId = ""
            prefs.userName = ""
            prefs.picture = ""
            prefs.googleSignIn = false
        }
    }

    var userId: String
        get() = prefs.getString(PREF_ID, "")
        set(value) = prefs.edit().putString(PREF_ID, value).apply()

    var token: String
        get() = prefs.getString(PREF_TOKEN, "")
        set(value) = prefs.edit().putString(PREF_TOKEN, value).apply()

    var googleSignIn: Boolean
        get() = prefs.getBoolean(PREF_GOOGLE, false)
        set(value) = prefs.edit().putBoolean(PREF_GOOGLE, value).apply()

    var providerId: String
        get() = prefs.getString(PREF_PROVIDER_ID, "")
        set(value) = prefs.edit().putString(PREF_PROVIDER_ID, value).apply()

    var picture: String
        get() = prefs.getString(PREF_PICTURE, "")
        set(value) = prefs.edit().putString(PREF_PICTURE, value).apply()

    var userName: String
        get() = prefs.getString(PREF_USERNAME, "")
        set(value) = prefs.edit().putString(PREF_USERNAME, value).apply()
}