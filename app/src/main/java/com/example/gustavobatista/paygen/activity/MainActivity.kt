package com.example.gustavobatista.paygen.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.entity.dataclass.ProviderDataClass
import com.example.gustavobatista.paygen.fragment.CheckedInFragment
import com.example.gustavobatista.paygen.fragment.ProvidersFragment
import com.example.gustavobatista.paygen.prefs
import com.example.gustavobatista.paygen.service.CustomerService
import com.example.gustavobatista.paygen.util.UserInfo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onStart() {
        super.onStart()
        checkReception()
    }

    private fun checkReception() {
        showProgress()
        CustomerService.checkReception(prefs.userId).applySchedulers().subscribe(
                {
                    when (it.name.isEmpty()) {
                        true -> {
                            fragmentManager.inTransaction { add(R.id.container, ProvidersFragment()) }
                        }
                        else -> {
                            ProviderDataClass.provider = it
                            fragmentManager.inTransaction { add(R.id.container, CheckedInFragment()) }
                        }
                    }
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

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_payment_methods -> {
                startActivity<PaymentMethodsActivity>()
            }
            R.id.nav_logout -> {
                UserInfo.clearData(this)
                startActivity(intentFor<SplashActivity>().clearTask().clearTop())
                finish()
            }
            R.id.nav_settings -> {
                startActivity<SettingsActivity>()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return false
    }


}
