package com.smith.ryan.dttest.feature.core.connectivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.IBinder
import android.util.Log

/**
 * Broadcast receiver meant to help better be aware to the user's online connectivity state during
 * the application's lifecycle. Handles the connectivity internals so you don't need to be directly
 * aware of some constant values and such.
 *
 * Broadcast is received for either ConnectivityManager.CONNECTIVITY_ACTION' or 'ConnectivityManager.
 *
 * Apps targeting Android 7.0 (API level 24) and higher do not receive this broadcast if they
 * declare the broadcast receiver in their manifest. Apps will still receive broadcasts if they
 * register their BroadcastReceiver with Context#registerReceiver Context.registerReceiver()
 * and that context is still valid.
 *
 * For a disconnect event, the boolean extra EXTRA_NO_CONNECTIVITY
 * is set to {@code true} if there are no connected networks at all.
 *
 * @deprecated >= N apps should use the more versatile {@link #requestNetwork},
 *             registerNetworkCallback or registerDefaultNetworkCallback
 *             functions instead for faster and more detailed updates about the network
 *             changes they care about.
 */
class ConnectivityReceiver: BroadcastReceiver() {

    override fun peekService(myContext: Context?, service: Intent?): IBinder {
        return super.peekService(myContext, service)
    }

    override fun onReceive(context: Context?, intent: Intent?) {

        intent?.let {

            // Doesn't matter which action let's just check the connection
            context?.let { cont ->
                // Posts the boolean to the Main Thread. Any views visible to the user should be able
                // to respond to the change.
                Log.d("ConnectivityReceiver", ".TAGS: onReceive() checking Connectivity")
                ConnectivityUtils.checkConnectivityLive(cont)
            }

            it
        }
    }

    companion object {

        private const val NETWORK_CALLBACK_UPDATE = "com.smith.ryan.dttest.extra.NETWORK_CALLBACK_UPDATE"

        /**
         * Returns an IntentFilter populated with the Connectivity related actions by the registering
         * context.
         *
         * @return IntentFilter
         */
        fun registrationIntentFilter(): IntentFilter {
            val filter = IntentFilter()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                filter.addAction(NETWORK_CALLBACK_UPDATE)
            } else {
                filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
            }

            return filter
        }
    }
}