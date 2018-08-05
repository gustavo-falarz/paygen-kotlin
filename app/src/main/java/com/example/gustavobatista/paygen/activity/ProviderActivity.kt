package com.example.gustavobatista.paygen.activity

import android.os.Bundle
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.entity.Provider
import com.example.gustavobatista.paygen.prefs
import com.example.gustavobatista.paygen.service.LobbyService
import com.example.gustavobatista.paygen.util.Constants
import com.example.gustavobatista.paygen.util.ImageUtil.load
import kotlinx.android.synthetic.main.activity_provider.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity

class ProviderActivity : BaseActivity() {

    lateinit var provider: Provider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider)

        provider = intent.getSerializableExtra(Constants.TRANSITION_KEY_PROVIDER) as Provider

        setupActionBar()
        setupToolbar(provider.name)
        imBannerProvider.load(provider.info.banner) { request -> request.fit() }

        btCheckin.setOnClickListener { onClickCheckin() }

    }

    private fun onClickCheckin() {
        LobbyService.checkIn(prefs.userId, provider.id).applySchedulers().subscribe(
                {
                    handleResult(it)
                },
                {
                    handleException(it)
                }
        )
    }

    private fun handleResult(message: String) {
        prefs.providerId = provider.id
        alert(getString(R.string.title_success), message) {
            positiveButton(R.string.yes) {
                startActivity<MainActivity>()
            }
        }.show()
    }
}
