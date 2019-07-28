package com.satya.utilites.extentions

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import com.blankj.utilcode.util.AppUtils
import com.satya.utilites.R
import com.satya.utilites.Utilities.Toolkit
import com.yarolegovich.lovelydialog.LovelyStandardDialog

enum class UpdateStatus {
	NO_UPDATE_AVAILABLE, NORMAL_UPDATE_AVAILABLE, FORCE_UPDATE_AVAILABLE, ERROR
}

fun checkFoUpdate(currentVersion: String, latestVersion: String, latestSafeVersion: String): UpdateStatus {
	if (currentVersion == latestVersion) return UpdateStatus.NO_UPDATE_AVAILABLE
	if (currentVersion < latestSafeVersion) return UpdateStatus.FORCE_UPDATE_AVAILABLE
	return if (currentVersion < latestVersion) UpdateStatus.NORMAL_UPDATE_AVAILABLE
	else UpdateStatus.ERROR
}

fun alertNormalUpdate(updateUrl: String, onLaterClicked: View.OnClickListener) {
	val activity = Toolkit.getTopActivityOrApp() as Activity
	LovelyStandardDialog(activity, LovelyStandardDialog.ButtonLayout.VERTICAL)
		.setTitle("به روز رسانی")
		.setMessage("نسخه جدید اپلیکیشن رسید")
		.setTopColorRes(color(R.color.md_black_1000))
		.setButtonsColorRes(color(R.color.md_white_1000))
		.setIcon(R.drawable.circle)
		.setPositiveButton("به روز رسانی") { activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl))) }
		.setNeutralButton("بعدا") { onLaterClicked.onClick(View(activity)) }
		.setNegativeButton("خروج") { AppUtils.exitApp() }
		.show()
}

fun alertForceUpdate(updateUrl: String) {
	val activity = Toolkit.getTopActivityOrApp() as Activity
	LovelyStandardDialog(activity, LovelyStandardDialog.ButtonLayout.VERTICAL)
		.setTitle("به روز رسانی")
		.setMessage("نسخه جدید اپلیکیشن رسید")
		.setTopColorRes(color(R.color.md_black_1000))
		.setButtonsColorRes(color(R.color.md_white_1000))
		.setIcon(R.drawable.circle)
		.setPositiveButton("به روز رسانی") { activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl))) }
		.setNegativeButton("خروج") { AppUtils.exitApp() }
		.show()
}