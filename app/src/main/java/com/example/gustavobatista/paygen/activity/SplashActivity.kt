package com.example.gustavobatista.paygen.activity

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.prefs
import com.example.gustavobatista.paygen.util.PermissionUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton

class SplashActivity : BaseActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun nextActivity() {
        if (prefs.token == "") {
            startActivity<LoginActivity>()
        } else {
            startActivity<MainActivity>()
        }
    }

    override fun onStart() {
        super.onStart()
        checkPermissions()
    }

    private fun checkPermissions() {
        if (!PermissionUtils.checkLocationPermission(getActivity())) {
            PermissionUtils.requestLocationPermission(getActivity())
        } else {
            getLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        prefs.latitude = location.latitude.toString()
                        prefs.longitude = location.longitude.toString()
                    }
                    nextActivity()
                }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            alert(getString(R.string.disclaimer_location_usage),
                    getString(R.string.error_title)) {
                yesButton {
                    checkPermissions()
                }
                noButton {
                    nextActivity()
                }
            }.show()
        } else {
            checkPermissions()
        }
    }
}
