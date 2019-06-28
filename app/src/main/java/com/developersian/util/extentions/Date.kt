package com.developersian.util.extentions

import android.app.Activity
import android.graphics.Color
import android.widget.TextView
import com.developersian.util.persian.PersianDate
import com.developersian.util.persian.PersianDateFormat
import ir.hamsaa.persiandatepicker.Listener
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.util.PersianCalendar

private fun getPersianDatePickerDialog(activity: Activity): PersianDatePickerDialog = PersianDatePickerDialog(activity).setPositiveButtonString("باشه").setNegativeButton("بیخیال").setTodayButton("امروز").setTodayButtonVisible(true).setMaxYear(PersianDatePickerDialog.THIS_YEAR).setMinYear(1300).setActionTextColor(Color.GRAY)

private fun parseSh(date: String): PersianDate = getPersianDateFormat().parse(date)

private fun parseGrg(date: String): PersianDate = getPersianDateFormat().parseGrg(date)

private fun getPersianDateFormat(): PersianDateFormat = PersianDateFormat("yyyy-MM-dd")

fun Activity.showDatePicker(textView: TextView) {
	textView.setOnClickListener {
		getPersianDatePickerDialog(this).setListener(object : Listener {
			override fun onDismissed() {}
			override fun onDateSelected(persianCalendar: PersianCalendar) {
				val month: String = if (persianCalendar.persianMonth.toString().length == 1) "0${persianCalendar.persianMonth}"
				else persianCalendar.persianMonth.toString()
				val day: String? = if (persianCalendar.persianDay.toString().length == 1) "0${persianCalendar.persianDay}"
				else persianCalendar.persianDay.toString()
				val persianDate = "${persianCalendar.persianYear}-$month-$day"
				val gregorianDate = parseSh(persianDate)
				
				val grgMonth: String = if (gregorianDate.grgMonth.toString().length == 1) "0${gregorianDate.grgMonth}"
				else gregorianDate.grgMonth.toString()
				val grgDay: String? = if (gregorianDate.grgDay.toString().length == 1) "0${gregorianDate.grgDay}"
				else gregorianDate.grgDay.toString()
				
				textView.text = persianDate.getPersianNumber().replace("-", "/")
				textView.tag = "${gregorianDate.grgYear}-$grgMonth-$grgDay"
			}
		}).show()
	}
}

fun grgToSh(date: String): String {
	val shamsiDate = parseGrg(date.substring(0, 10))
	return "${shamsiDate.shYear}/${append0(shamsiDate.shMonth.toString())}/${append0(shamsiDate.shDay.toString())}"
}

fun append0(input: String): String = if (input.length == 1) "0$input" else input