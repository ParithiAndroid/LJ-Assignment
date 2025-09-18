package com.parithidb.ljassignment.util

import android.content.Context
import android.net.ConnectivityManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * General-purpose utility functions for the app.
 */

class Common {
    companion object {
        /**
         * Checks if the device is connected to the Internet.
         * @param context Application context
         * @return true if network is connected
         */
        fun isInternetConnected(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}