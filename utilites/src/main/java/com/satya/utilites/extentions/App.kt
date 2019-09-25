package com.satya.utilites.extentions

import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.CleanUtils
import com.blankj.utilcode.util.CrashUtils

fun exitApp() = AppUtils.exitApp()

fun versionName() = AppUtils.getAppVersionName()

fun versionCode() = AppUtils.getAppVersionCode()

fun cleanApp() {
	CleanUtils.cleanExternalCache()
	CleanUtils.cleanInternalCache()
	CleanUtils.cleanInternalDbs()
	CleanUtils.cleanInternalFiles()
	CleanUtils.cleanInternalSp()
}

fun crashInfo(error: (String, Throwable) -> Unit) = CrashUtils.init { crashInfo, e -> error(crashInfo, e) }