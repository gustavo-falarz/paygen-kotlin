package com.example.gustavobatista.paygen.util

import android.content.Context
import android.content.SharedPreferences
import com.example.gustavobatista.paygen.PaygenApplication.Companion.prefs
import com.example.gustavobatista.paygen.entity.User
import com.example.gustavobatista.paygen.util.Constants.PREF_EMAIL
import com.example.gustavobatista.paygen.util.Constants.PREF_GOOGLE
import com.example.gustavobatista.paygen.util.Constants.PREF_ID
import com.example.gustavobatista.paygen.util.Constants.PREF_KEY
import com.example.gustavobatista.paygen.util.Constants.PREF_PROVIDER_ID
import com.firebase.ui.auth.AuthUI

/**
 * Created by Gustavo on 12/26/2017.
 */
class UserInfo(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_KEY, 0)

    companion object {
        fun saveUserLocally(user: User, google: Boolean) {
            prefs.userEmail = user.email
            prefs.userId = user.id
            prefs.googleSignIn = google
        }

        fun clearData(context: Context) {
            AuthUI.getInstance()
                    .signOut(context)
                    .addOnCompleteListener {
                    }
            prefs.userEmail = ""
            prefs.userId = ""
            prefs.googleSignIn = false
        }
    }

    var userId: String
        get() = prefs.getString(PREF_ID, "5b34409e27039805549d3951")
        set(value) = prefs.edit().putString(PREF_ID, value).apply()

    var userEmail: String
        get() = prefs.getString(PREF_EMAIL, "")
        set(value) = prefs.edit().putString(PREF_EMAIL, value).apply()

    var googleSignIn: Boolean
        get() = prefs.getBoolean(PREF_GOOGLE, false)
        set(value) = prefs.edit().putBoolean(PREF_GOOGLE, value).apply()

    var providerId: String
        get() = prefs.getString(PREF_PROVIDER_ID, "")
        set(value) = prefs.edit().putString(PREF_PROVIDER_ID, value).apply()
}