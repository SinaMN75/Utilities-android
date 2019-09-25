package com.satya.utilites.extentions

import android.app.Activity
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.ScreenUtils
import com.satya.utilites.utilities.Toolkit

fun sdkVersion(): Int = DeviceUtils.getSDKVersionCode()

fun screenWidth(): Int = ScreenUtils.getScreenWidth().pxToDp()
fun screenHeight(): Int = ScreenUtils.getScreenHeight().pxToDp()

fun setPortrate() = ScreenUtils.setPortrait(Toolkit.getTopActivityOrApp() as Activity)
fun setLandscape() = ScreenUtils.setLandscape(Toolkit.getTopActivityOrApp() as Activity)

fun isPortrate() = ScreenUtils.isPortrait()
fun isLandscape() = ScreenUtils.isLandscape()
