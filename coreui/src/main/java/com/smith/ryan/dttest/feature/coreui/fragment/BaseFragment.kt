package com.smith.ryan.dttest.feature.coreui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.smith.ryan.dttest.feature.core.connectivity.ConnectivityUtils

/**
 * Abstract implementation of Fragment that handles observation of the user's online connectivity
 * status.
 */
abstract class BaseFragment: Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ConnectivityUtils.connectivityStatus.observe(viewLifecycleOwner, Observer { status ->
            status?.let {
                onConnectivityStatus(it)
            }
        })
    }

    /**
     * Will be called when the connectivity status of the user's device changes.
     *
     * True = Online | False = Offline
     */
    abstract fun onConnectivityStatus(isConnected: Boolean)
}