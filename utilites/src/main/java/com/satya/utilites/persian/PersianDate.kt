package com.satya.utilites.persian

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.floor

@SuppressLint("SimpleDateFormat") class PersianDate {
	var time: Long? = null
		private set
	internal var shYear: Int = 0
	internal var shMonth: Int = 0
	internal var shDay: Int = 0
	internal var grgYear: Int = 0
	internal var grgMonth: Int = 0
	internal var grgDay: Int = 0
	internal var hour: Int = 0
	internal var minute: Int = 0
	internal var second: Int = 0
	private val grgSumOfDays = arrayOf(intArrayOf(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365), intArrayOf(0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366))
	private val hshSumOfDays = arrayOf(intArrayOf(0, 31, 62, 93, 124, 155, 186, 216, 246, 276, 306, 336, 365), intArrayOf(0, 31, 62, 93, 124, 155, 186, 216, 246, 276, 306, 336, 366))
	private val dayNames = arrayOf("شنبه", "یک‌شنبه", "دوشنبه", "سه‌شنبه", "چهارشنبه", "پنج‌شنبه", "جمعه")
	private val monthNames = arrayOf("فروردین", "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند")
	
	val isLeap: Boolean get() = this.isLeap(this.shYear)
	
	val monthDays: Int get() = this.getMonthDays(this.getShYear(), this.getShMonth())
	
	val dayInYear: Int get() = this.getDayInYear(this.getShMonth(), getShDay())
	
	val dayuntilToday: Long get() = this.getDayUntilToday(PersianDate())
	
	val isMidNight: Boolean? get() = this.hour < 12
	
	val shortTimeOfTheDay: String get() = if (this.isMidNight!!) AM_SHORT_NAME else PM_SHORT_NAME
	
	val timeOfTheDay: String get() = if (this.isMidNight!!) AM_NAME else PM_NAME
	
	constructor() {
		this.time = Date().time
		this.changeTime()
	}
	
	constructor(timeInMilliSecond: Long?) {
		this.time = timeInMilliSecond
		this.changeTime()
	}
	
	constructor(date: Date) {
		this.time = date.time
		this.changeTime()
	}
	
	fun getShYear(): Int = shYear
	
	fun setShYear(shYear: Int): PersianDate {
		this.shYear = shYear
		this.prepareDate2(this.getShYear(), this.getShMonth(), this.getShDay())
		return this
	}
	
	fun getShMonth(): Int = shMonth
	
	fun setShMonth(shMonth: Int): PersianDate {
		this.shMonth = shMonth
		this.prepareDate2(this.getShYear(), this.getShMonth(), this.getShDay())
		return this
	}
	
	fun getShDay(): Int = shDay
	
	fun setShDay(shDay: Int): PersianDate {
		this.shDay = shDay
		this.prepareDate2(this.getShYear(), this.getShMonth(), this.getShDay())
		return this
	}
	
	fun getGrgYear(): Int = grgYear
	
	fun setGrgYear(grgYear: Int): PersianDate {
		this.grgYear = grgYear
		prepareDate()
		return this
	}
	
	fun getGrgMonth(): Int = grgMonth
	
	fun setGrgMonth(grgMonth: Int): PersianDate {
		this.grgMonth = grgMonth
		prepareDate()
		return this
	}
	
	fun getGrgDay(): Int = grgDay
	
	fun setGrgDay(grgDay: Int): PersianDate {
		this.grgDay = grgDay
		prepareDate()
		return this
	}
	
	fun getHour(): Int = hour
	
	fun setHour(hour: Int): PersianDate {
		this.hour = hour
		prepareDate()
		return this
	}
	
	fun getMinute(): Int = minute
	
	fun setMinute(minute: Int): PersianDate {
		this.minute = minute
		prepareDate()
		return this
	}
	
	fun getSecond(): Int = second
	
	fun setSecond(second: Int): PersianDate {
		this.second = second
		prepareDate()
		return this
	}
	
	fun initGrgDate(year: Int, month: Int, day: Int): PersianDate = this.initGrgDate(year, month, day, 0, 0, 0)
	
	fun initGrgDate(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): PersianDate {
		this.grgYear = year
		this.grgMonth = month
		this.grgDay = day
		this.hour = hour
		this.minute = minute
		this.second = second
		this.setGrgYear(year).setGrgMonth(month).setGrgDay(day).setHour(hour).setMinute(minute).setSecond(second)
		val convert = this.toJalali(year, month, day)
		this.shYear = convert[0]
		this.shMonth = convert[1]
		this.shDay = convert[2]
		this.setShYear(convert[0]).setShMonth(convert[1]).setShDay(convert[2])
		return this
	}
	
	fun initJalaliDate(year: Int, month: Int, day: Int): PersianDate = this.initJalaliDate(year, month, day, 0, 0, 0)
	
	fun initJalaliDate(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): PersianDate {
		this.setShYear(year).setShMonth(month).setShDay(day).setHour(hour).setMinute(minute).setSecond(second)
		val convert = this.toGregorian(year, month, day)
		this.setGrgYear(convert[0]).setGrgMonth(convert[1]).setGrgDay(convert[2])
		return this
	}
	
	private fun prepareDate2(year: Int, month: Int, day: Int): PersianDate {
		val convert = this.toGregorian(year, month, day)
		this.grgYear = convert[0]
		this.grgMonth = convert[1]
		this.setGrgDay(convert[2])
		return this
	}
	
	private fun prepareDate() {
		val dtStart = "" + this.textNumberFilter("" + this.getGrgYear()) + "-" +
		              this.textNumberFilter("" + this.getGrgMonth()) + "-" +
		              this.textNumberFilter("" + this.getGrgDay()) + "T" +
		              this.textNumberFilter("" + this.getHour()) + ":" +
		              this.textNumberFilter("" + this.getMinute()) + ":" +
		              this.textNumberFilter("" + this.getSecond()) + "Z"
		val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
		val convert = this.toJalali(this.getGrgYear(), this.getGrgMonth(), this.getGrgDay())
		this.shYear = convert[0]
		this.shMonth = convert[1]
		this.shDay = convert[2]
		val date: Date?
		try {
			date = format.parse(dtStart)
			this.time = date!!.time
		} catch (e: ParseException) {
			e.printStackTrace()
		}
	}
	
	fun grgIsLeap(Year: Int): Boolean = Year % 4 == 0 && (Year % 100 != 0 || Year % 400 == 0)
	
	fun isLeap(year: Int): Boolean {
		val referenceYear = 1375.0
		var startYear = 1375.0
		val yearRes = year - referenceYear
		if (yearRes > 0) {
			if (yearRes >= 33) {
				val numb = yearRes / 33
				startYear = referenceYear + floor(numb) * 33
			}
		} else {
			startYear = if (yearRes >= -33) {
				referenceYear - 33
			} else {
				val numb = abs(yearRes / 33)
				referenceYear - (floor(numb) + 1) * 33
			}
		}
		val leapYears = doubleArrayOf(startYear, startYear + 4, startYear + 8, startYear + 16, startYear + 20, startYear + 24, startYear + 28, startYear + 33)
		return Arrays.binarySearch(leapYears, year.toDouble()) >= 0
	}
	
	fun toJalali(year: Int, month: Int, day: Int): IntArray {
		var hshDay = 0
		var hshMonth = 0
		var hshElapsed: Int
		var hshYear = year - 621
		val grgLeap = this.grgIsLeap(year)
		var hshLeap = this.isLeap(hshYear - 1)
		val grgElapsed = grgSumOfDays[if (grgLeap) 1 else 0][month - 1] + day
		val XmasToNorooz = if (hshLeap && grgLeap) 80 else 79
		if (grgElapsed <= XmasToNorooz) {
			hshElapsed = grgElapsed + 286
			hshYear--
			if (hshLeap && !grgLeap)
				hshElapsed++
		} else {
			hshElapsed = grgElapsed - XmasToNorooz
			hshLeap = this.isLeap(hshYear)
		}
		if (year >= 2029 && (year - 2029) % 4 == 0) {
			hshElapsed++
		}
		for (i in 1..12) {
			if (hshSumOfDays[if (hshLeap) 1 else 0][i] >= hshElapsed) {
				hshMonth = i
				hshDay = hshElapsed - hshSumOfDays[if (hshLeap) 1 else 0][i - 1]
				break
			}
		}
		return intArrayOf(hshYear, hshMonth, hshDay)
	}
	
	fun toGregorian(year: Int, month: Int, day: Int): IntArray {
		var grgYear = year + 621
		var grgDay = 0
		var grgMonth = 0
		var grgElapsed: Int
		var hshLeap = this.isLeap(year)
		var grgLeap = this.grgIsLeap(grgYear)
		val hshElapsed = hshSumOfDays[if (hshLeap) 1 else 0][month - 1] + day
		if (month > 10 || month == 10 && hshElapsed > 286 + if (grgLeap) 1 else 0) {
			grgElapsed = hshElapsed - (286 + if (grgLeap) 1 else 0)
			grgLeap = grgIsLeap(++grgYear)
		} else {
			hshLeap = this.isLeap(year - 1)
			grgElapsed = hshElapsed + 79 + (if (hshLeap) 1 else 0) - if (grgIsLeap(grgYear - 1)) 1 else 0
		}
		if (grgYear >= 2030 && (grgYear - 2030) % 4 == 0)
			grgElapsed--
		for (i in 1..12) {
			if (grgSumOfDays[if (grgLeap) 1 else 0][i] >= grgElapsed) {
				grgMonth = i
				grgDay = grgElapsed - grgSumOfDays[if (grgLeap) 1 else 0][i - 1]
				break
			}
		}
		return intArrayOf(grgYear, grgMonth, grgDay)
	}
	
	fun dayOfWeek(): Int = this.dayOfWeek(this)
	
	fun dayOfWeek(date: PersianDate): Int = this.dayOfWeek(date.toDate())
	
	fun dayOfWeek(date: Date): Int {
		val cal = Calendar.getInstance()
		cal.time = date
		return cal.get(Calendar.DAY_OF_WEEK)
	}
	
	fun monthName(): String = this.monthName(this.getShMonth())
	
	fun monthName(month: Int): String = this.monthNames[month - 1]
	
	fun dayName(): String = this.dayName(this)
	
	fun dayName(date: PersianDate): String = this.dayNames[this.dayOfWeek(date)]
	
	fun getMonthDays(Year: Int, month: Int): Int {
		if (month == 12 && !this.isLeap(Year)) return 29
		return if (month <= 6) 31 else 30
	}
	
	fun getDayInYear(month: Int, day: Int): Int {
		var d = day
		for (i in 1 until month) d += if (i <= 6) 31 else 30
		return d
	}
	
	fun addDate(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): PersianDate {
		this.time = this.time?.plus(((year * 365 + month * 30 + day) * 24 * 3600 * 1000).toLong())
		this.time = this.time?.plus(((second + hour * 3600 + minute * 60) * 1000).toLong())
		this.changeTime()
		return this
	}
	
	fun addDate(year: Int, month: Int, day: Int): PersianDate = this.addDate(year, month, day, 0, 0, 0)
	
	fun addYear(year: Int): PersianDate = this.addDate(year, 0, 0, 0, 0, 0)
	
	fun addMonth(month: Int): PersianDate = this.addDate(0, month, 0, 0, 0, 0)
	
	fun addWeek(week: Int): PersianDate = this.addDate(0, 0, week * 7, 0, 0, 0)
	
	fun addDay(day: Int): PersianDate = this.addDate(0, 0, day, 0, 0, 0)
	
	fun after(dateInput: PersianDate): Boolean? = this.time!! < dateInput.time!!
	
	fun before(dateInput: PersianDate): Boolean? = !this.after(dateInput)!!
	
	fun equals(dateInput: PersianDate): Boolean? = this.time === dateInput.time
	
	operator fun compareTo(anotherDate: PersianDate): Int {
		return when {
			this.time!! < anotherDate.time!! -> -1
			this.time === anotherDate.time -> 0
			else -> 1
		}
	}
	
	fun getDayUntilToday(date: PersianDate): Long = this.untilToday(date)[0]
	
	fun untilToday(): LongArray = this.untilToday(PersianDate())
	
	fun untilToday(date: PersianDate): LongArray {
		val secondsInMilli: Long = 1000
		val minutesInMilli = secondsInMilli * 60
		val hoursInMilli = minutesInMilli * 60
		val daysInMilli = hoursInMilli * 24
		var different = abs(this.time!! - date.time!!)
		val elapsedDays = different / daysInMilli
		different %= daysInMilli
		val elapsedHours = different / hoursInMilli
		different %= hoursInMilli
		val elapsedMinutes = different / minutesInMilli
		different %= minutesInMilli
		val elapsedSeconds = different / secondsInMilli
		return longArrayOf(elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds)
	}
	
	override fun toString(): String = PersianDateFormat.format(this, null)
	
	fun toDate(): Date = Date(this.time!!)
	
	private fun textNumberFilter(date: String): String = if (date.length < 2) "0$date" else date
	
	private fun changeTime() = this.initGrgDate(Integer.parseInt(SimpleDateFormat("yyyy").format(this.time)), Integer.parseInt(SimpleDateFormat("MM").format(this.time)), Integer.parseInt(SimpleDateFormat("dd").format(this.time)), Integer.parseInt(SimpleDateFormat("HH").format(this.time)), Integer.parseInt(SimpleDateFormat("mm").format(this.time)), Integer.parseInt(SimpleDateFormat("ss").format(this.time)))
	
	fun startOfDay(persianDate: PersianDate): PersianDate = persianDate.setHour(0).setMinute(0).setSecond(0)
	
	fun startOfDay(): PersianDate = this.startOfDay(this)
	
	fun endOfDay(persianDate: PersianDate): PersianDate = persianDate.setHour(23).setMinute(59).setSecond(59)
	
	fun endOfDay(): PersianDate = this.endOfDay(this)
	
	fun isMidNight(persianDate: PersianDate): Boolean? = persianDate.isMidNight
	
	fun getShortTimeOfTheDay(persianDate: PersianDate): String = if (persianDate.isMidNight!!) AM_SHORT_NAME else PM_SHORT_NAME
	
	fun getTimeOfTheDay(persianDate: PersianDate): String = if (persianDate.isMidNight!!) AM_NAME else PM_NAME
	
	companion object {
		val FARVARDIN = 1
		val ORDIBEHEST = 2
		val KHORDAD = 3
		val TIR = 4
		val MORDAD = 5
		val SHAHRIVAR = 6
		val MEHR = 7
		val ABAN = 8
		val AZAR = 9
		val DAY = 10
		val BAHMAN = 11
		val ESFAND = 12
		val AM = 1
		val PM = 2
		const val AM_SHORT_NAME = "ق.ظ"
		const val PM_SHORT_NAME = "ب.ظ"
		const val AM_NAME = "قبل از ظهر"
		const val PM_NAME = "بعد از ظهر"
		
		fun isJalaliLeap(year: Int): Boolean = PersianDate().isLeap(year)
		
		fun isGrgLeap(year: Int): Boolean = PersianDate().grgIsLeap(year)
		
		fun today(): PersianDate {
			val pDate = PersianDate()
			pDate.setHour(0).setMinute(0).setSecond(0)
			return pDate
		}
		
		fun tomorrow(): PersianDate {
			val pDate = PersianDate()
			pDate.addDay(1)
			pDate.setHour(0).setMinute(0).setSecond(0)
			return pDate
		}
	}
}