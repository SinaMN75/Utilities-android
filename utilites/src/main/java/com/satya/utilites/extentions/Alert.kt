package com.satya.utilites.extentions

import android.app.Activity
import android.view.View
import com.blankj.utilcode.util.SnackbarUtils
import com.blankj.utilcode.util.ToastUtils
import com.satya.utilites.R
import com.satya.utilites.Utilities.OnItemSelectedListener
import com.satya.utilites.Utilities.OnItemsSelectedListener
import com.satya.utilites.Utilities.OnTextInputConfirmListener
import com.yarolegovich.lovelydialog.LovelyChoiceDialog
import com.yarolegovich.lovelydialog.LovelyInfoDialog
import com.yarolegovich.lovelydialog.LovelyStandardDialog
import com.yarolegovich.lovelydialog.LovelyTextInputDialog

fun View.snackBarAction(text: String, actionText: String, onClickListener: View.OnClickListener, bgColor: Int = R.color.colorPrimary, textColor: Int = R.color.colorAccent, actionTextColor: Int = R.color.colorAccent) = SnackbarUtils.with(this).setBgColor(color(bgColor)).setMessage(text).setAction(actionText, color(actionTextColor), onClickListener).setDuration(SnackbarUtils.LENGTH_LONG).show()!!

fun View.snackBar(text: String, bgColor: Int = R.color.colorPrimary, textColor: Int = R.color.colorAccent) = SnackbarUtils.with(this).setBgColor(color(bgColor)).setMessageColor(color(textColor)).setMessage(text).setDuration(SnackbarUtils.LENGTH_SHORT).show()!!

fun toastShort(text: String, bgColor: Int = R.color.colorPrimary, textColor: Int = R.color.md_white_1000) {
	ToastUtils.setBgColor(color(bgColor))
	ToastUtils.setMsgColor(color(textColor))
	ToastUtils.showShort(text)
}

fun toastLong(text: String, bgColor: Int = R.color.colorPrimary, textColor: Int = R.color.md_white_1000) {
	ToastUtils.setBgColor(color(bgColor))
	ToastUtils.setMsgColor(color(textColor))
	ToastUtils.showLong(text)
}

fun Activity.dialogStandard(title: String, message: String, onPositiveClick: View.OnClickListener, icon: Int = R.drawable.circle, topColor: Int = R.color.colorPrimary, buttonColor: Int = R.color.colorAccent) {
	LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.VERTICAL)
		.setTitle(title)
		.setMessage(message)
		.setTopColorRes(topColor)
		.setButtonsColorRes(buttonColor)
		.setIcon(icon)
		.setPositiveButton(android.R.string.ok) { onPositiveClick.onClick(View(this)) }
		.setNegativeButton(android.R.string.no, null)
		.show()
}

fun Activity.dialogInfo(title: String, message: String, buttonTitle: String = "OK", showAgain: Boolean = false, notShowAgainId: Int = 0, icon: Int = R.drawable.circle, topColor: Int = R.color.colorPrimary, buttonColor: Int = R.color.colorAccent) {
	LovelyInfoDialog(this)
		.setTopColorRes(topColor)
		.setIcon(icon)
		.setConfirmButtonColor(buttonColor)
		.setConfirmButtonText(buttonTitle)
		.setNotShowAgainOptionEnabled(notShowAgainId)
		.setNotShowAgainOptionChecked(showAgain)
		.setTitle(title)
		.setMessage(message)
		.show()
}

fun Activity.dialogSingleChoice(title: String, message: String, items: ArrayList<String>, onItemSelected: OnItemSelectedListener<String>, buttonTitle: String = "OK", icon: Int = R.drawable.circle, topColor: Int = R.color.colorPrimary, buttonColor: Int = R.color.colorAccent) {
	LovelyChoiceDialog(this)
		.setTopColorRes(topColor)
		.setTitle(title)
		.setConfirmButtonColor(buttonColor)
		.setConfirmButtonText(buttonTitle)
		.setIcon(icon)
		.setMessage(message)
		.setItems(items) { position, item -> onItemSelected.onItemSelected(position, item) }
		.show()
}

fun Activity.dialogMultiChoice(title: String, message: String, list: ArrayList<String>, onItemSelected: OnItemsSelectedListener<String>, buttonTitle: String = "OK", icon: Int = R.drawable.circle, topColor: Int = R.color.colorPrimary, buttonColor: Int = R.color.colorAccent) {
	LovelyChoiceDialog(this)
		.setTopColorRes(topColor)
		.setMessage(message)
		.setConfirmButtonColor(buttonColor)
		.setTitle(title)
		.setIcon(icon)
		.setItemsMultiChoice(list) { positions, items -> onItemSelected.onItemsSelected(positions, items) }.setConfirmButtonText(buttonTitle)
		.show()
}

fun Activity.dialogInput(title: String, message: String, onTextInputConfirmListener: OnTextInputConfirmListener, buttonTitle: String = "OK", icon: Int = R.drawable.circle, topColor: Int = R.color.colorPrimary, buttonColor: Int = R.color.colorAccent) {
	LovelyTextInputDialog(this)
		.setTopColorRes(topColor)
		.setTitle(title)
		.setConfirmButtonColor(buttonColor)
		.setMessage(message)
		.setIcon(icon)
		.setConfirmButton(buttonTitle) { text -> onTextInputConfirmListener.onTextInputConfirmed(text) }
		.show()
}

