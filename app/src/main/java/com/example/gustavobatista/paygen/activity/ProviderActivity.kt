package com.example.gustavobatista.paygen.activity

import android.os.Bundle
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.entity.OpenHours
import com.example.gustavobatista.paygen.entity.Provider
import com.example.gustavobatista.paygen.prefs
import com.example.gustavobatista.paygen.service.LobbyService
import com.example.gustavobatista.paygen.util.Constants
import com.example.gustavobatista.paygen.util.ImageUtil.load
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_provider.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity

class ProviderActivity : BaseActivity(), OnMapReadyCallback {
    lateinit var mMap: GoogleMap

    lateinit var provider: Provider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider)

        provider = intent.getSerializableExtra(Constants.TRANSITION_KEY_PROVIDER) as Provider

        setupActionBar()
        setupToolbar(provider.name)

        imBannerProvider.load(provider.info.banner) { request -> request.fit() }
        tvOpenHours.text = processOpenHours(provider.info.openHours)
        tvAbout.text = provider.info.about
        tvAddress.text = provider.info.address
        val mapFragment = fragmentManager.findFragmentById(R.id.map) as MapFragment

        mapFragment.getMapAsync(this)
        btCheckin.setOnClickListener { onClickCheckin() }

    }

    private fun onClickCheckin() {
        showProgress()
        LobbyService.checkIn(prefs.userId, provider.id).applySchedulers().subscribe(
                {
                    closeProgress()
                    handleResult(it)
                },
                {
                    handleException(it)
                },
                {
                    closeProgress()
                }
        )
    }

    private fun handleResult(message: String) {
        prefs.providerId = provider.id
        alert(message, getString(R.string.title_success)) {
            positiveButton(R.string.yes) {
                startActivity<MainActivity>()
            }
        }.show()
    }

    private fun processOpenHours(openHours: List<OpenHours>): String {
        var result = ""
        for (openHour in openHours) {
            result += openHour
        }
        return result
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        val position = LatLng(provider.location.x, provider.location.y)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15.0F))

        googleMap.addMarker(MarkerOptions()
                .position(position)
                .title(provider.name)
                .snippet(provider.info.address))
    }
}
