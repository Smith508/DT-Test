package com.smith.ryan.dttest.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.smith.ryan.dttest.R
import com.smith.ryan.dttest.feature.core.connectivity.ConnectivityReceiver
import com.smith.ryan.dttest.feature.core.connectivity.ConnectivityUtils

class MainActivity : AppCompatActivity() {

    private val connectivityReceiver =
        ConnectivityReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Registering for connectivity events.
        registerReceiver(connectivityReceiver, ConnectivityReceiver.registrationIntentFilter())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {// >= N
            ConnectivityUtils.registerDefaultNetworkCallback(applicationContext)
        }
    }

    override fun onResume() {
        super.onResume()
        ConnectivityUtils.checkConnectivityLive(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(connectivityReceiver)
    }
}