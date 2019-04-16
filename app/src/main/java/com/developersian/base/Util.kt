package com.developersian.base

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextSwitcher
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.SnackbarUtils
import com.blankj.utilcode.util.ToastUtils
import com.developersian.R
import com.developersian.util.Utilities.Toolkit
import com.developersian.util.persian.PersianDate
import com.developersian.util.persian.PersianDateFormat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.squareup.picasso.Picasso
import ir.hamsaa.persiandatepicker.Listener
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.util.PersianCalendar
import java.io.File
import java.io.IOException
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class Util {
	
	private val sharedPreferences = App.context.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
	
	fun log(tag: String, message: String) = Log.i(tag, message)
	fun log(tag: String, message: Int) = Log.i(tag, message.toString())
	fun log(tag: String, message: Float) = Log.i(tag, message.toString())
	fun log(tag: String, message: Double) = Log.i(tag, message.toString())
	fun log(tag: String, message: JsonObject) = Log.i(tag, message.toString())
	
	fun dpToPx(resources: Resources, dp: Float): Float = dp * resources.displayMetrics.density + 0.5f
	
	fun spToPx(resources: Resources, sp: Float): Float = sp * resources.displayMetrics.scaledDensity
	
	fun showKeyboard() = Objects.requireNonNull(App.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
	
	fun hideKeyboard(view: View, activity: Activity) {
		val inputMethodManager: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
	}
	
	fun hideKeyboardOnClick(view: View, activity: Activity) = view.setOnClickListener { hideKeyboard(view, activity) }
	
	fun playFromAsset(path: String) {
		try {
			val descriptor = App.context.assets.openFd(path)
			val mediaPlayer = android.media.MediaPlayer()
			mediaPlayer.setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
			mediaPlayer.prepare()
			mediaPlayer.start()
			descriptor.close()
		} catch (e: IOException) {
			e.printStackTrace()
		}
	}
	
	fun getHtmlLayout(content: String, textColor: String, backgroundColor: String): String = "<!DOCTYPE html> <html> <head> <link rel=\"stylesheet\" href=\"style.css\"> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> </head> <body style='color:$textColor background-color:$backgroundColor'>$content</body></html>"
	
	fun loadData(webView: WebView, content: String, textColor: String, backgroundColor: String, mimeType: String = "text/html", encoding: String = "utf-8") = webView.loadData(getHtmlLayout(content, textColor, backgroundColor), mimeType, encoding)
	
	// View
	
	fun visible(vararg view: View) {
		for (i in view) i.visibility = View.VISIBLE
	}
	
	fun invisible(vararg view: View) {
		for (i in view) i.visibility = View.INVISIBLE
	}
	
	fun gone(vararg view: View) {
		for (i in view) i.visibility = View.GONE
	}
	
	// Resources
	
	fun color(color: Int) = ContextCompat.getColor(App.context, color)
	
	fun drawable(drawable: Int) = ContextCompat.getDrawable(App.context, drawable)!!
	
	fun bitmapFromDrawable(drawable: Int) = ImageUtils.getBitmap(drawable)!!
	
	fun animation(animation: Int): Animation = AnimationUtils.loadAnimation(App.context, animation)
	
	fun array(array: Int): Array<String> = App.context.resources.getStringArray(array)
	
	fun setDrawable(imageView: ImageView, drawable: Int) = imageView.setImageDrawable(ContextCompat.getDrawable(App.context, drawable))
	
	fun persianMonth(): Array<String> = array(R.array.persianMonth)
	
	fun grgMonth(): Array<String> = array(R.array.grgMonths)
	
	// String
	
	fun isTextEmpty(view: TextView): Boolean = view.text.toString().trim().isEmpty()
	fun isTextEmpty(text: String): Boolean = text.trim().isEmpty()
	
	fun getTrimmedString(view: TextView): String = view.text.toString().trim()
	fun getTrimmedString(text: String): String = text.trim()
	
	fun validatePhoneNumber(phoneNumber: String): Boolean = getTrimmedString(phoneNumber).length in 10..14 && getTrimmedString(phoneNumber).substring(0, 3).contains("0") || getTrimmedString(phoneNumber).substring(0, 3).contains("9")
	
	fun lengthOfEditText(view: TextView): Int = getTrimmedString(view).length
	
	fun validateInputByLength(editText: EditText, errorText: String, length: Int): Boolean = if (lengthOfEditText(editText) < length) {
		editText.error = errorText
		false
	} else true
	
	fun showMoney(long: Long): String = DecimalFormat("#,###,###").format(long)
	fun showMoneyAsTyping(editText: EditText, editable: Editable, watcher: TextWatcher) {
		editText.removeTextChangedListener(watcher)
		try {
			var originalString = editable.toString()
			if (originalString.contains(",")) originalString = originalString.replace(",".toRegex(), "")
			val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
			formatter.applyPattern("#,###,###,###")
			val formattedString = formatter.format(java.lang.Long.parseLong(originalString))
			editText.setText(formattedString)
			editText.setSelection(editText.text.length)
		} catch (nfe: NumberFormatException) {
			nfe.printStackTrace()
		}
		editText.addTextChangedListener(watcher)
	}
	
	fun ageFromDate(year: Int): Int = Calendar.getInstance().get(Calendar.YEAR) - year
	
	fun toJson(string: String): JsonObject = JsonParser().parse(string).asJsonObject
	
	fun getPersianNumber(text: String): String {
		var textPost = text
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
	
	fun getEnglishNumber(text: String): String {
		var textPost = text
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
	
	fun getPersianDay(text: String): String {
		var textPost = text
		textPost = textPost.replace("Sunday", "یک شنبه")
		textPost = textPost.replace("Monday", "دو شنبه")
		textPost = textPost.replace("Tuesday", "سه شنبه")
		textPost = textPost.replace("Wednesday", "چهار شنبه")
		textPost = textPost.replace("Thursday", "پنج شنبه")
		textPost = textPost.replace("Friday", "جمعه")
		textPost = textPost.replace("Saturday", "شنبه")
		return textPost
	}
	
	fun getEnglishDay(text: String): String {
		var textPost = text
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
	
	fun getPersianMonth(text: String): String {
		var textPost = text
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
	
	fun getEnglishMonth(text: String): String {
		var textPost = text
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
	
	// Date
	
	private fun getPersianDatePickerDialog(activity: Activity): PersianDatePickerDialog = PersianDatePickerDialog(activity).setPositiveButtonString("باشه").setNegativeButton("بیخیال").setTodayButton("امروز").setTodayButtonVisible(true).setMaxYear(PersianDatePickerDialog.THIS_YEAR).setMinYear(1300).setActionTextColor(Color.GRAY)
	
	private fun parseSh(date: String): PersianDate = getPersianDateFormat().parse(date)
	
	private fun parseGrg(date: String): PersianDate = getPersianDateFormat().parseGrg(date)
	
	private fun getPersianDateFormat(): PersianDateFormat = PersianDateFormat("yyyy-MM-dd")
	
	fun showDatePicker(textView: TextView, activity: Activity) {
		textView.setOnClickListener {
			getPersianDatePickerDialog(activity).setListener(object : Listener {
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
					
					textView.text = getPersianNumber(persianDate).replace("-", "/")
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
	
	//Alert
	
	fun snackBarAction(view: View, bgColor: Int, actionTextColor: Int, text: String, actionText: String, onClickListener: View.OnClickListener) = SnackbarUtils.with(view).setBgColor(color(bgColor)).setMessage(text).setAction(actionText, color(actionTextColor), onClickListener).setDuration(SnackbarUtils.LENGTH_LONG).show()
	
	fun snackBar(view: View, bgColor: Int, text: String) = SnackbarUtils.with(view).setBgColor(color(bgColor)).setMessage(text).setDuration(SnackbarUtils.LENGTH_SHORT).show()
	
	fun toastShort(text: String, bgColor: Int = R.color.colorPrimary, textColor: Int = R.color.md_white_1000, isLong: Boolean = false) {
		ToastUtils.setBgColor(color(bgColor))
		ToastUtils.setMsgColor(color(textColor))
		if (isLong) ToastUtils.showShort(text) else ToastUtils.showLong(text)
	}
	
	// Animation
	
	fun animateAlpha(vararg views: View, startDelay: Long, duration: Long, alpha: Float) {
		for (i in views) i.animate().setStartDelay(startDelay).setDuration(duration).alpha(alpha).start()
	}
	
	fun animateMove(vararg views: View, startDelay: Long, duration: Long, x: Float, y: Float) {
		for (i in views) i.animate().setStartDelay(startDelay).setDuration(duration).translationX(x).translationY(y).start()
	}
	
	fun animateRotate(vararg views: View, startDelay: Long, duration: Long, rotate: Float) {
		for (i in views) i.animate().setStartDelay(startDelay).setDuration(duration).rotation(rotate).start()
	}
	
	fun handleTextSwitcher(textSwitcher: TextSwitcher, textSize: Float, textColor: Int) {
		textSwitcher.inAnimation = animation(android.R.anim.slide_in_left)
		textSwitcher.outAnimation = animation(android.R.anim.slide_out_right)
		textSwitcher.setFactory {
			val textView = TextView(App.context)
			textView.textSize = textSize
			textView.setTextColor(color(textColor))
			textView.gravity = Gravity.START
			textView.typeface = Typeface.createFromAsset(App.context.assets, "FONT")
			textView
		}
	}
	
	// RecyclerView
	
	fun linearLayoutForRecyclerView(vararg recyclerViews: RecyclerView, nested: Boolean = true, hasFixedSize: Boolean = true) {
		for (i in recyclerViews) {
			i.layoutManager = LinearLayoutManager(App.context)
			i.isNestedScrollingEnabled = nested
			i.setHasFixedSize(hasFixedSize)
		}
	}
	
	fun linearLayoutForRecyclerViewHorizontal(vararg recyclerViews: RecyclerView, nested: Boolean = true, hasFixedSize: Boolean = true) {
		for (i in recyclerViews) {
			i.layoutManager = LinearLayoutManager(App.context, LinearLayoutManager.HORIZONTAL, false)
			i.isNestedScrollingEnabled = nested
			i.setHasFixedSize(hasFixedSize)
		}
	}
	
	fun gridLayoutForRecyclerView(vararg recyclerViews: RecyclerView, spanCount: Int, nested: Boolean = true, hasFixedSize: Boolean = true) {
		for (i in recyclerViews) {
			i.layoutManager = GridLayoutManager(App.context, spanCount)
			i.isNestedScrollingEnabled = nested
			i.setHasFixedSize(hasFixedSize)
		}
	}
	
	// Activity & Fragment
	
	fun startActivity(clz: Class<out Activity>, vararg extra: String = arrayOf(""), enterAnim: Int = android.R.anim.slide_in_left, exitAnim: Int = android.R.anim.slide_out_right) {
		val context = Toolkit.getTopActivityOrApp()
		val bundle = Bundle()
		for (index in 0 until extra.size) bundle.putString(index.toString(), extra[index])
		startActivity(context, bundle, context.packageName, clz.name, getOptionsBundle(context, enterAnim, exitAnim))
	}
	
	fun startActivity(clz: Class<out Activity>, options: Bundle, extraArray: ArrayList<String>? = null, vararg extra: String = arrayOf("")) {
		val context = Toolkit.getTopActivityOrApp()
		val bundle = Bundle()
		for (index in 0 until extra.size) bundle.putString(index.toString(), extra[index])
		bundle.putStringArrayList("array", extraArray)
		startActivity(context, bundle, context.packageName, clz.name, options)
	}
	
	fun startActivityNoStack(targetActivity: Class<*>) = Toolkit.getTopActivityOrApp().startActivity(Intent(Toolkit.getTopActivityOrApp(), targetActivity).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
	
	fun intent(key: String): String? {
		val activity = Toolkit.getTopActivityOrApp() as Activity
		return activity.intent.getStringExtra(key)
	}
	
	fun navigate(fragment: Fragment, navDestination: Int, vararg extra: String = arrayOf("")) {
		val bundle = Bundle()
		for (index in 0 until extra.size) bundle.putString(index.toString(), extra[index])
		fragment.findNavController().navigate(navDestination, bundle)
	}
	
	fun showDialog(fragment: DialogFragment, fragmentManager: FragmentManager, vararg extra: String = arrayOf("")) {
		val bundle = Bundle()
		for (index in 0 until extra.size) bundle.putString(index.toString(), extra[index])
		fragment.arguments = bundle
		fragment.show(fragmentManager, "")
	}
	
	fun argument(key: String, fragment: Fragment): String = fragment.arguments!!.getString(key)!!
	
	fun picasso(url: String?, imageView: ImageView, placeholder: Int) {
		if (url != "") Picasso.get().load(url).placeholder(drawable(placeholder)).into(imageView)
		else setDrawable(imageView, placeholder)
	}
	
	fun picasso(url: String?, imageView: ImageView) {
		if (url != "") Picasso.get().load(url).into(imageView)
	}
	
	// Map
	
	fun projection(googleMap: GoogleMap): Foursome<LatLng, LatLng, LatLng, LatLng> {
		val projection = googleMap.projection.visibleRegion.latLngBounds
		val northEast = LatLng(projection.northeast.latitude, projection.northeast.longitude) // North East of the Screen.
		val northWest = LatLng(projection.southwest.latitude, projection.northeast.longitude) // North West of the Screen.
		val southWest = LatLng(projection.southwest.latitude, projection.southwest.longitude) // South West of the Screen.
		val southEast = LatLng(projection.northeast.latitude, projection.southwest.longitude) // South East of the Screen.
		return Foursome(northEast, northWest, southWest, southEast)
	}
	
	// SharedPreferences ///////////////////////////////////////////////////////////////////////////////////////////////////
	
	fun put(key: String, value: String) = sharedPreferences.edit().putString(key, value).apply()
	
	fun put(key: String, value: Int) = sharedPreferences.edit().putInt(key, value).apply()
	
	fun put(key: String, value: Float) = sharedPreferences.edit().putFloat(key, value).apply()
	
	fun put(key: String, value: Boolean) = sharedPreferences.edit().putBoolean(key, value).apply()
	
	fun put(key: String, value: Long) = sharedPreferences.edit().putLong(key, value).apply()
	
	fun getString(key: String, defaultValue: String = "null"): String = sharedPreferences.getString(key, defaultValue)!!
	
	fun getInt(key: String, defaultValue: Int = -1): Int = sharedPreferences.getInt(key, defaultValue)
	
	fun getFloat(key: String, defaultValue: Float = -1F): Float = sharedPreferences.getFloat(key, defaultValue)
	
	fun getBoolean(key: String, defaultValue: Boolean = false): Boolean = sharedPreferences.getBoolean(key, defaultValue)
	
	fun getLong(key: String, defaultValue: Long = -1L): Long = sharedPreferences.getLong(key, defaultValue)
	
	fun clearAll() = sharedPreferences.edit().clear().apply()
	
	// File
	
	fun isFileExist(filePath: String): Boolean = File(filePath).exists()
	
	fun openFile(context: Context, fileName: String, filePath: String, type: String) = if (Build.VERSION.SDK_INT >= 26) context.startActivity(Intent(Intent.ACTION_VIEW).setDataAndType(FileProvider.getUriForFile(context, context.applicationContext.packageName + ".my.package.name.provider", File(filePath, "$fileName.pdf")), type).addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION))
	else context.startActivity(Intent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).setAction(Intent.ACTION_VIEW).setDataAndType(Uri.fromFile(File("$filePath$fileName")), type))
	
	
	//
	//	fun download(url: String, fileName: String, onDownload: Download) {
	//		PRDownloader.download(url, "/sdcard/Mimic/", fileName).build().setOnPauseListener {}.setOnCancelListener {}.setOnStartOrResumeListener {}.setOnProgressListener { progress ->
	//			onDownload.onProgressListener(progress)
	//		}.start(object : OnDownloadListener {
	//			override fun onError(error: com.downloader.Error?) {}
	//			override fun onDownloadComplete() {
	//				onDownload.onDownloadComplete()
	//			}
	//		})
	//	}
	
	private fun getOptionsBundle(context: Context, enterAnim: Int, exitAnim: Int): Bundle? = ActivityOptionsCompat.makeCustomAnimation(context, enterAnim, exitAnim).toBundle()
	private fun startActivity(context: Context, extras: Bundle?, pkg: String, cls: String, options: Bundle?) {
		val intent = Intent(Intent.ACTION_VIEW)
		if (extras != null) intent.putExtras(extras)
		intent.component = ComponentName(pkg, cls)
		if (context !is Activity) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		if (options != null) context.startActivity(intent, options)
		else context.startActivity(intent)
	}
	
	class Foursome<out A, out B, out C, out D>(val first: A, val second: B, val third: C, val forth: D)
}