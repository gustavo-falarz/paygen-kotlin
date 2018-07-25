package com.example.gustavobatista.paygen.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.adapter.ProviderAdapter
import com.example.gustavobatista.paygen.entity.Provider
import com.example.gustavobatista.paygen.service.ProviderService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_providers.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        recyclerViewProviders.layoutManager = LinearLayoutManager(this)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        getProviders()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun getProviders() {
        showProgress()
        ProviderService.getProviders().applySchedulers().subscribe(
                {
                    setAdapter(it)
                    closeProgress()
                },
                {
                    handleException(it)
                    closeProgress()
                }
        )
    }

    private fun setAdapter(providers: List<Provider>) {
        val adapter = ProviderAdapter(providers)
        recyclerViewProviders.adapter = adapter
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
            R.id.nav_providers -> {
                startActivity<ProvidersActivity>()
            }
            R.id.nav_logout -> {
                startActivity<LoginActivity>()
                finish()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
