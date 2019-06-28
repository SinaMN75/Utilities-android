package com.developersian.util.extentions

import android.app.Activity
import android.view.View
import com.blankj.utilcode.util.SnackbarUtils
import com.blankj.utilcode.util.ToastUtils
import com.developersian.R
import com.developersian.base.App
import com.yarolegovich.lovelydialog.LovelyChoiceDialog
import com.yarolegovich.lovelydialog.LovelyInfoDialog
import com.yarolegovich.lovelydialog.LovelyStandardDialog
import com.yarolegovich.lovelydialog.LovelyTextInputDialog

fun snackBarAction(view: View, bgColor: Int, actionTextColor: Int, text: String, actionText: String, onClickListener: View.OnClickListener) = SnackbarUtils.with(view).setBgColor(color(bgColor)).setMessage(text).setAction(actionText, color(actionTextColor), onClickListener).setDuration(SnackbarUtils.LENGTH_LONG).show()!!

fun snackBar(view: View, bgColor: Int, text: String) = SnackbarUtils.with(view).setBgColor(color(bgColor)).setMessage(text).setDuration(SnackbarUtils.LENGTH_SHORT).show()!!

fun toastShort(text: String, bgColor: Int = R.color.colorPrimary, textColor: Int = R.color.md_white_1000, isLong: Boolean = false) {
	ToastUtils.setBgColor(color(bgColor))
	ToastUtils.setMsgColor(color(textColor))
	if (isLong) ToastUtils.showShort(text) else ToastUtils.showShort(text)
}

fun toastLong(text: String, bgColor: Int = R.color.colorPrimary, textColor: Int = R.color.md_white_1000, isLong: Boolean = false) {
	ToastUtils.setBgColor(color(bgColor))
	ToastUtils.setMsgColor(color(textColor))
	if (isLong) ToastUtils.showShort(text) else ToastUtils.showLong(text)
}

fun Activity.dialogStandard(title: String, message: String, onPositiveClick: View.OnClickListener, icon: Int = R.drawable.ic_launcher_background, topColor: Int = R.color.colorPrimary, buttonColor: Int = R.color.colorAccent) {
	LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.VERTICAL)
		.setTopColorRes(topColor)
		.setButtonsColorRes(buttonColor)
		.setIcon(icon)
		.setTitle(title)
		.setMessage(message)
		.setPositiveButton(android.R.string.ok) { onPositiveClick.onClick(View(App.context)) }
		.setNegativeButton(android.R.string.no, null)
		.show()
}

fun Activity.dialogInfo(title: String, message: String, showAgain: Boolean = false, notShowAgainId: Int = 0, icon: Int = R.drawable.ic_launcher_background, topColor: Int = R.color.colorPrimary) {
	LovelyInfoDialog(this)
		.setTopColorRes(topColor)
		.setIcon(icon)
		.setNotShowAgainOptionEnabled(notShowAgainId)
		.setNotShowAgainOptionChecked(showAgain)
		.setTitle(title)
		.setMessage(message)
		.show()
}

fun Activity.dialogSingleChoice(title: String, message: String, items: ArrayList<String>, onItemSelected: LovelyChoiceDialog.OnItemSelectedListener<String>, icon: Int = R.drawable.ic_launcher_background, topColor: Int = R.color.colorPrimary) {
	LovelyChoiceDialog(this)
		.setTopColorRes(topColor)
		.setTitle(title)
		.setIcon(icon)
		.setMessage(message)
		.setItems(items) { position, item -> onItemSelected.onItemSelected(position, item) }
		.show()
}

fun Activity.dialogMultiChoice(title: String, message: String, list: ArrayList<String>, onItemSelected: LovelyChoiceDialog.OnItemsSelectedListener<String>, buttonTitle: String = "OK", icon: Int = R.drawable.ic_launcher_background, topColor: Int = R.color.colorPrimary) {
	LovelyChoiceDialog(this)
		.setTopColorRes(topColor)
		.setMessage(message)
		.setTitle(title)
		.setIcon(icon)
		.setItemsMultiChoice(list) { positions, items -> onItemSelected.onItemsSelected(positions, items) }.setConfirmButtonText(buttonTitle)
		.show()
}

fun Activity.dialogInput(title: String, message: String, onTextInputConfirmListener: LovelyTextInputDialog.OnTextInputConfirmListener, icon: Int = R.drawable.ic_launcher_background, topColor: Int = R.color.colorPrimary, buttonColor: Int = R.color.colorAccent) {
	LovelyTextInputDialog(this)
		.setTopColorRes(topColor)
		.setTitle(title)
		.setMessage(message)
		.setIcon(icon)
		.setConfirmButton(android.R.string.ok) { text -> onTextInputConfirmListener.onTextInputConfirmed(text) }
		.show()
}

