package com.example.gustavobatista.paygen.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.entity.User
import com.example.gustavobatista.paygen.entity.dto.LoginDTO
import com.example.gustavobatista.paygen.prefs
import com.example.gustavobatista.paygen.service.AccessService
import com.example.gustavobatista.paygen.util.Constants
import com.example.gustavobatista.paygen.util.UserInfo
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.*
import java.util.*

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btSignUp.setOnClickListener { startActivity<AddCustomerActivity>() }
        btLogin.setOnClickListener { onClickLogin() }
        btSignInWithGoogle.setOnClickListener { onClickGoogleSignIn() }
    }

    override fun onStart() {
        super.onStart()
        UserInfo.clearData(this)
    }

    private fun onClickGoogleSignIn() {
        showProgress()
        val providers = Arrays.asList(
                AuthUI.IdpConfig.GoogleBuilder().build())

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(providers)
                        .build(),
                Constants.RC_SIGN_IN)
    }

    private fun onClickLogin() {
        if (etEmail.text.isNullOrEmpty() || etPassword.text.isNullOrEmpty()) {
            showWarning(R.string.warning_empty_fields)
            return
        }
        showProgress()
        AccessService.validate(etEmail.text.toString(), etPassword.text.toString()).applySchedulers()
                .subscribe(
                        {
                            handleLogin(it)
                        },
                        {
                            closeProgress()
                            handleException(it)
                        },
                        {
                            closeProgress()
                        }
                )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constants.RC_SIGN_IN -> {
                val response = IdpResponse.fromResultIntent(data)
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        val user = FirebaseAuth.getInstance().currentUser
                        if (user != null) {
                            onUserValidated(user.email, user.displayName)
                        }
                    }
                    else -> {
                        closeProgress()
                        showWarning(response.toString())
                    }
                }
            }
        }
    }

    private fun handleLogin(loginDTO: LoginDTO) {
        prefs.token = loginDTO.token
        prefs.userId = loginDTO.userId
        when {
            loginDTO.status == User.Status.PENDING -> handleStatusPending()
            else -> nextActivity()
        }
    }

    private fun handleStatusPending() {
        alert(getString(R.string.disclaimer_status_pending), getString(R.string.title_success)) {
            yesButton { startActivity<ChangePasswordActivity>() }
        }.show()
    }

    private fun onUserValidated(email: String?, name: String?) {
        AccessService.googleSignin(email!!, name!!).applySchedulers().subscribe(
                {
                    handleGoogleSignIn(it)
                },
                {
                    closeProgress()
                    handleException(it)
                },
                {
                    closeProgress()
                }
        )
    }

    private fun handleGoogleSignIn(loginDTO: LoginDTO) {
        prefs.userId = loginDTO.userId
        prefs.token = loginDTO.token
        prefs.googleSignIn = true

        nextActivity()
    }

    private fun nextActivity() {
        closeProgress()
        startActivity(intentFor<MainActivity>().clearTask().clearTop())
        finish()
    }


}
