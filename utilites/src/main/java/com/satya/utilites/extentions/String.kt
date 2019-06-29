package com.satya.utilites.extentions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun TextView.getTrimmedString(): String = this.text.toString().trim()

fun String.validatePhoneNumber(): Boolean = this.trim().length in 10..14 && this.trim().substring(0, 3).contains("0") || this.trim().substring(0, 3).contains("9")

fun TextView.lengthOfEditText(): Int = this.getTrimmedString().length

fun EditText.validateInputByLength(errorText: String, length: Int): Boolean = if (this.lengthOfEditText() < length) {
	this.error = errorText
	false
} else true

fun showMoney(long: Long): String = DecimalFormat("#,###,###").format(long)

fun EditText.showMoneyAsTyping(editable: Editable, watcher: TextWatcher) {
	this.removeTextChangedListener(watcher)
	try {
		var originalString = editable.toString()
		if (originalString.contains(",")) originalString = originalString.replace(",".toRegex(), "")
		val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
		formatter.applyPattern("#,###,###,###")
		val formattedString = formatter.format(java.lang.Long.parseLong(originalString))
		this.setText(formattedString)
		this.setSelection(this.text.length)
	} catch (nfe: NumberFormatException) {
		nfe.printStackTrace()
	}
	this.addTextChangedListener(watcher)
}

fun Int.ageFromDate(): Int = Calendar.getInstance().get(Calendar.YEAR) - this

fun String.toJson(): JsonObject = JsonParser().parse(this).asJsonObject

fun String.getPersianNumber(): String {
	var textPost = this
	textPost = textPost.replace("1", "۱")
	textPost = textPost.replace("2", "۲")
	textPost = textPost.replace("3", "۳")
	textPost = textPost.replace("4", "۴")
	textPost = textPost.replace("5", "۵")
	textPost = textPost.replace("6", "۶")
	textPost = textPost.replace("7", "۷")
	textPost = textPost.replace("8", "۸")
	textPost = textPost.replace("9", "۹")
	textPost = textPost.replace("0", "۰")
	return textPost
}

fun String.getEnglishNumber(): String {
	var textPost = this
	textPost = textPost.replace("۱", "1")
	textPost = textPost.replace("۲", "2")
	textPost = textPost.replace("۳", "3")
	textPost = textPost.replace("۴", "4")
	textPost = textPost.replace("۵", "5")
	textPost = textPost.replace("۶", "6")
	textPost = textPost.replace("۷", "7")
	textPost = textPost.replace("۸", "8")
	textPost = textPost.replace("۹", "9")
	textPost = textPost.replace("۰", "0")
	return textPost
}

fun String.getPersianDay(): String {
	var textPost = this
	textPost = textPost.replace("Sunday", "یک شنبه")
	textPost = textPost.replace("Monday", "دو شنبه")
	textPost = textPost.replace("Tuesday", "سه شنبه")
	textPost = textPost.replace("Wednesday", "چهار شنبه")
	textPost = textPost.replace("Thursday", "پنج شنبه")
	textPost = textPost.replace("Friday", "جمعه")
	textPost = textPost.replace("Saturday", "شنبه")
	return textPost
}

fun String.getEnglishDay(): String {
	var textPost = this
	textPost = textPost.replace("یک شنبه", "Sunday")
	textPost = textPost.replace("دو‌شنبه", "Monday")
	textPost = textPost.replace("سه‌شنبه", "Tuesday")
	textPost = textPost.replace("چهار‌شنبه", "Tuesday")
	textPost = textPost.replace("پنج‌شنبه", "Thursday")
	textPost = textPost.replace("جمعه", "Friday")
	textPost = textPost.replace("جمعه", "Friday")
	textPost = textPost.replace("شنبه", "Saturday")
	return textPost
}

fun String.getPersianMonth(): String {
	var textPost = this
	textPost = textPost.replace("01", "فروردین")
	textPost = textPost.replace("02", "اردیبهشت")
	textPost = textPost.replace("03", "خرداد")
	textPost = textPost.replace("04", "تیر")
	textPost = textPost.replace("05", "مرداد")
	textPost = textPost.replace("06", "شهریور")
	textPost = textPost.replace("07", "مهر")
	textPost = textPost.replace("08", "آبان")
	textPost = textPost.replace("09", "آذر")
	textPost = textPost.replace("10", "دی")
	textPost = textPost.replace("11", "بهمن")
	textPost = textPost.replace("12", "اسفند")
	return textPost
}

fun String.getEnglishMonth(): String {
	var textPost = this
	textPost = textPost.replace("01", "January")
	textPost = textPost.replace("02", "February")
	textPost = textPost.replace("03", "March")
	textPost = textPost.replace("04", "April")
	textPost = textPost.replace("05", "May")
	textPost = textPost.replace("06", "June")
	textPost = textPost.replace("07", "July")
	textPost = textPost.replace("08", "August")
	textPost = textPost.replace("09", "September")
	textPost = textPost.replace("10", "October")
	textPost = textPost.replace("11", "November")
	textPost = textPost.replace("12", "December")
	return textPost
}