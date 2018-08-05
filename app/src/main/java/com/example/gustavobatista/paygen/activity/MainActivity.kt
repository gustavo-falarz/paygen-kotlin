package com.example.gustavobatista.paygen.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.fragment.CheckedInFragment
import com.example.gustavobatista.paygen.fragment.ProvidersFragment
import com.example.gustavobatista.paygen.prefs
import com.example.gustavobatista.paygen.service.CustomerService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
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
                    when (it) {
                        true -> fragmentManager.inTransaction { add(R.id.container, CheckedInFragment()) }
                        false -> fragmentManager.inTransaction { add(R.id.container, ProvidersFragment()) }
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_payment_methods -> {
                startActivity<PaymentMethodsActivity>()
            }
            R.id.nav_logout -> {
                startActivity<LoginActivity>()
                finish()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return false
    }


}
