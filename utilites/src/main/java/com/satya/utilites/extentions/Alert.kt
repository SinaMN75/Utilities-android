package com.satya.utilites.extentions

import android.app.Activity
import android.view.View
import com.blankj.utilcode.util.SnackbarUtils
import com.blankj.utilcode.util.ToastUtils
import com.satya.utilites.R
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

fun Activity.dialogStandard(title: String = "Title", message: String = "", icon: Int = R.drawable.circle, topColor: Int = R.color.colorPrimary, buttonColor: Int = R.color.colorAccent, positiveButtonText: String = "OK", negativeButtonText: String = "No", neutralButtonText: String = "Cancel", onPositiveClick: View.OnClickListener? = null, onNegativeClick: View.OnClickListener? = null, onNeutralClick: View.OnClickListener? = null) {
	LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.VERTICAL)
		.setTitle(title)
		.setMessage(message)
		.setTopColorRes(topColor)
		.setButtonsColorRes(buttonColor)
		.setIcon(icon)
		.setPositiveButton(positiveButtonText) { onPositiveClick?.onClick(View(this)) }
		.setNegativeButton(negativeButtonText) { onNegativeClick?.onClick(View(this)) }
		.setNeutralButton(neutralButtonText) { onNeutralClick?.onClick(View(this)) }
		.show()
}

fun Activity.dialogInfo(title: String = "Title", message: String = "", buttonTitle: String = "OK", showAgain: Boolean = false, notShowAgainId: Int = 0, icon: Int = R.drawable.circle, topColor: Int = R.color.colorPrimary, buttonColor: Int = R.color.colorAccent) {
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

fun Activity.dialogSingleChoice(title: String = "Title", message: String = "", items: ArrayList<String>, onItemSelected: (Int, String) -> Unit, icon: Int = R.drawable.circle, topColor: Int = R.color.colorPrimary) {
	LovelyChoiceDialog(this)
		.setTopColorRes(topColor)
		.setTitle(title)
		.setIcon(icon)
		.setMessage(message)
		.setItems(items) { position, item -> onItemSelected(position, item) }
		.show()
}

fun Activity.dialogMultiChoice(title: String = "", message: String = "", list: ArrayList<String>, onItemSelected: (MutableList<Int>, MutableList<String>) -> Unit, buttonTitle: String = "OK", icon: Int = R.drawable.circle, topColor: Int = R.color.colorPrimary, buttonColor: Int = R.color.colorAccent) {
	LovelyChoiceDialog(this)
		.setTopColorRes(topColor)
		.setMessage(message)
		.setConfirmButtonColor(buttonColor)
		.setTitle(title)
		.setIcon(icon)
		.setItemsMultiChoice(list) { positions, items -> onItemSelected(positions, items) }.setConfirmButtonText(buttonTitle)
		.show()
}

fun Activity.dialogInput(title: String = "", message: String = "", onTextInputConfirmListener: (String) -> Unit, buttonTitle: String = "OK", icon: Int = R.drawable.circle, topColor: Int = R.color.colorPrimary, buttonColor: Int = R.color.colorAccent) {
	LovelyTextInputDialog(this)
		.setTopColorRes(topColor)
		.setTitle(title)
		.setConfirmButtonColor(buttonColor)
		.setMessage(message)
		.setIcon(icon)
		.setConfirmButton(buttonTitle) { text -> onTextInputConfirmListener(text) }
		.show()
}

//fun alertDialog(context: Context, titleText: String, dec: String) {
//	val view = LayoutInflater.from(context).inflate(R.layout.dialog_alert, null)
//
//	view.textViewTitleAlertDialog.text = titleText
//	view.textViewDecAlertDialog.text = dec
//	view.buttonOKAlertDialog.text = context.getString(R.string.ok)
//
//	val alertDialog = AlertDialog.Builder(context).setView(view).create()
//	alertDialog.setCancelable(false)
//	alertDialog.show()
//	view.buttonOKAlertDialog.setOnClickListener { alertDialog.dismiss() }
//}