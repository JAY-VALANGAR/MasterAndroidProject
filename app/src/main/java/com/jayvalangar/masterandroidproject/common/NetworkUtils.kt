package com.jayvalangar.masterandroidproject.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities


// This is a helper object to check if the phone has an internet connection.
object NetworkUtils {

    // Checks if the phone is connected to Wi-Fi or mobile data.
    fun isNetworkAvailable(context: Context): Boolean {

        // Gets the tool to check the phone’s network.
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // Gets the current network, returns false if none.
        val network = connectivityManager.activeNetwork ?: return false

        // Gets the network details, returns false if none.
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        // Returns true if connected to Wi-Fi or mobile data.
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }
}