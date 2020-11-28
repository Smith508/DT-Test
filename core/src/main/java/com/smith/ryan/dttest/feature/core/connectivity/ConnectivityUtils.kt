package com.smith.ryan.dttest.feature.core.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Helps manage the Connectivity Logic.
 *
 * The basic 'isOnline' Boolean check is available when LiveData implementation is not needed.
 */
object ConnectivityUtils {

    private const val TAG = "ConnectivityUtils"

    /**
     *  MutableLiveData connectivity status to be modified only by the VM.
     *  UI will only have access to the public LiveData representation.
     */
    private val mutableConnectivityStatus = MutableLiveData<Boolean?>()

    /**
     * LiveData representation of the connectivity status from our adapter to be
     * exposed to the UI to observe.
     */
    val connectivityStatus = mutableConnectivityStatus as LiveData<Boolean?>

    /**
     * Checks the Connectivity status of the user's device for a simple is Online | is Offline response.
     * Handles version checking on min sdks.
     *
     * @param context
     *
     * @return True if connected | False if not.
     */
    fun isOnline(context: Context): Boolean {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// >= M

            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .activeNetwork != null

        } else {// < M

            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .activeNetworkInfo?.isConnected == true
        }
    }

    /**
     * Checks the Connectivity status of the user's device for a simple is Online | is Offline response.
     * Posts the result to the 'connectivityStatus' MutableLiveData property contained in this class.
     * Handles version checking on min sdks.
     *
     * @param context
     *
     * @return True if connected | False if not.
     */
    fun checkConnectivityLive(context: Context): Boolean {
        return isOnline(context).let {
            mutableConnectivityStatus.postValue(it)
            it
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun registerDefaultNetworkCallback(context: Context) {

        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            .registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
                    Log.d(TAG, ".TAGS: onBlockedStatusChanged() ")
                    super.onBlockedStatusChanged(network, blocked)
                }

                override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                }

                /**
                 * Currently there is a race condition between when 'onLost' is called and when the
                 * Network object returned by the ConnectivityManager for the context nulls itself out(means offline).
                 *
                 * We can do a check internally for the previous(deprecated) 'activeNetworkInfo.isConnected' flag, but this
                 * isn't the best case for production. It can be useful for debugging purposes, but for now we are going to use
                 * out a workaround.
                 *
                 * Since 'onLost' is called when "the framework has a hard loss of the network or when the
                 * graceful failure ends." We are going to manually set the 'connectivityStatus'
                 * live data to false.
                 */
                override fun onLost(network: Network) {
                    Log.d(TAG, ".TAGS: onLost() ")
                    super.onLost(network)
                    mutableConnectivityStatus.postValue(false)
                }

                override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
                    super.onLinkPropertiesChanged(network, linkProperties)
                }

                override fun onUnavailable() {
                    Log.d(TAG, ".TAGS: onUnavailable() ")
                    super.onUnavailable()
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    Log.d(TAG, ".TAGS: onLosing() ")
                    super.onLosing(network, maxMsToLive)
                }

                override fun onAvailable(network: Network) {
                    Log.d(TAG, ".TAGS: onAvailable() ")
                    super.onAvailable(network)
                    checkConnectivityLive(context)
                }
            })
    }
}