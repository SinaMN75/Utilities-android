package com.satya.utilites.persian

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat") class PersianDateFormat {
	private val key = arrayOf("a", "l", "j", "F", "Y", "H", "i", "s", "d", "g", "n", "m", "t", "w", "y", "z", "A", "L")
	private var pattern: String? = null
	private val keyParse = arrayOf("yyyy", "MM", "dd", "HH", "mm", "ss")
	
	constructor(pattern: String) {
		this.pattern = pattern
	}
	
	constructor() {
		pattern = "l j F Y H:i:s"
	}
	
	fun format(date: PersianDate): String {
		var year2: String? = null
		year2 = when {
			("" + date.shYear).length == 2 -> "" + date.shYear
			("" + date.shYear).length == 3 -> ("" + date.shYear).substring(2, 3)
			else -> ("" + date.shYear).substring(2, 4)
		}
		val values = arrayOf(if (date.isMidNight!!) "ق.ظ" else "ب.ظ", date.dayName(), "" + date.shDay, date.monthName(), "" + date.shYear, this.textNumberFilter("" + date.hour), this.textNumberFilter("" + date.minute), this.textNumberFilter("" + date.second), this.textNumberFilter("" + date.shDay), "" + date.hour, "" + date.shMonth, this.textNumberFilter("" + date.shMonth), "" + date.monthDays, "" + date.dayOfWeek(), year2, "" + date.dayInYear, date.timeOfTheDay, if (date.isLeap) "1" else "0")
		return this.stringUtils(this.pattern, this.key, values)
	}
	
	@Throws(ParseException::class)
	fun parse(date: String): PersianDate {
		return this.parse(date, this.pattern)
	}
	
	@Throws(ParseException::class)
	fun parse(date: String, pattern: String?): PersianDate {
		val jalaliDate = object : ArrayList<Int>() {
			init {
				add(0)
				add(0)
				add(0)
				add(0)
				add(0)
				add(0)
			}
		}
		for (i in keyParse.indices) {
			if (pattern!!.contains(keyParse[i])) {
				val startTemp = pattern.indexOf(keyParse[i])
				val endTemp = startTemp + keyParse[i].length
				val dateReplace = date.substring(startTemp, endTemp)
				if (dateReplace.matches("[-+]?\\d*\\.?\\d+".toRegex())) jalaliDate[i] = Integer.parseInt(dateReplace)
				else throw ParseException("Parse Exception", 10)
			}
		}
		return PersianDate().initJalaliDate(jalaliDate[0], jalaliDate[1], jalaliDate[2], jalaliDate[3], jalaliDate[4], jalaliDate[5])
	}
	
	@Throws(ParseException::class)
	fun parseGrg(date: String): PersianDate = this.parseGrg(date, this.pattern)
	
	@Throws(ParseException::class)
	fun parseGrg(date: String, pattern: String?): PersianDate = PersianDate(SimpleDateFormat(pattern!!).parse(date)!!.time)
	
	private fun stringUtils(text: String?, key: Array<String>, values: Array<String>): String {
		var t = text
		for (i in key.indices) t = t!!.replace(key[i], values[i])
		return t!!
	}
	
	private fun textNumberFilter(date: String): String = if (date.length < 2) "0$date" else date
	
	companion object {
		fun format(date: PersianDate, pattern: String?): String {
			var p = pattern
			if (p == null) p = "l j F Y H:i:s"
			val key = arrayOf("a", "l", "j", "F", "Y", "H", "i", "s", "d", "g", "n", "m", "t", "w", "y", "z", "A", "L")
			val year2: String = when {
				("" + date.shYear).length == 2 -> "" + date.shYear
				("" + date.shYear).length == 3 -> ("" + date.shYear).substring(2, 3)
				else -> ("" + date.shYear).substring(2, 4)
			}
			val values = arrayOf(date.shortTimeOfTheDay, date.dayName(), "" + date.shDay, date.monthName(), "" + date.shYear, textNumberFilterStatic("" + date.hour), textNumberFilterStatic("" + date.minute), textNumberFilterStatic("" + date.second), textNumberFilterStatic("" + date.shDay), "" + date.hour, "" + date.shMonth, textNumberFilterStatic("" + date.shMonth), "" + date.monthDays, "" + date.dayOfWeek(), year2, "" + date.dayInYear, date.timeOfTheDay, if (date.isLeap) "1" else "0")
			for (i in key.indices) p = p!!.replace(key[i], values[i])
			return p!!
		}
		
		fun textNumberFilterStatic(date: String): String = if (date.length < 2) "0$date" else date
	}
}