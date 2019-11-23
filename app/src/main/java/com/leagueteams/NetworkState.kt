package com.leagueteams

import android.content.Context
import android.net.ConnectivityManager

class NetworkState {

        fun verifyAvailableNetwork(contect:Context):Boolean{
            val connectivityManager=contect.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo=connectivityManager.activeNetworkInfo
            return  networkInfo!=null && networkInfo.isConnected
    }

}