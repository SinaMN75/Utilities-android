package com.developersian.util.extentions

import android.view.View
import com.blankj.utilcode.util.SnackbarUtils
import com.blankj.utilcode.util.ToastUtils
import com.developersian.R

fun snackBarAction(view: View, bgColor: Int, actionTextColor: Int, text: String, actionText: String, onClickListener: View.OnClickListener) = SnackbarUtils.with(view).setBgColor(color(bgColor)).setMessage(text).setAction(actionText, color(actionTextColor), onClickListener).setDuration(SnackbarUtils.LENGTH_LONG).show()

fun snackBar(view: View, bgColor: Int, text: String) = SnackbarUtils.with(view).setBgColor(color(bgColor)).setMessage(text).setDuration(SnackbarUtils.LENGTH_SHORT).show()

fun toastShort(text: String, bgColor: Int = R.color.colorPrimary, textColor: Int = R.color.md_white_1000, isLong: Boolean = false) {
	ToastUtils.setBgColor(color(bgColor))
	ToastUtils.setMsgColor(color(textColor))
	if (isLong) ToastUtils.showShort(text) else ToastUtils.showLong(text)
}