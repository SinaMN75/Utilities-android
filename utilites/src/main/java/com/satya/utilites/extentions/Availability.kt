package com.satya.utilites.extentions

import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.Manifest.permission.ACCESS_WIFI_STATE
import androidx.annotation.RequiresPermission
import com.blankj.utilcode.util.NetworkUtils

@RequiresPermission(ACCESS_NETWORK_STATE) fun isConnected() = NetworkUtils.isConnected()

@RequiresPermission(ACCESS_WIFI_STATE) fun turnOnWifi() = NetworkUtils.setWifiEnabled(true)

