package com.satya.utilites.extentions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import androidx.core.content.FileProvider
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.Utils
import com.google.gson.JsonObject
import com.satya.utilites.utilities.Toolkit
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Foursome<out A, out B, out C, out D>(val first: A, val second: B, val third: C, val forth: D)

fun log(tag: String, message: Any) = Log.i(tag, message.toString())

fun Int.dpToPx(): Int = ConvertUtils.dp2px(this.toFloat())
fun Int.spToPx(): Int = ConvertUtils.sp2px(this.toFloat())
fun Int.pxToDp(): Int = ConvertUtils.px2dp(this.toFloat())
fun Int.pxToSp(): Int = ConvertUtils.px2sp(this.toFloat())

fun showKeyBoard() = KeyboardUtils.showSoftInput(Toolkit.getTopActivityOrApp() as Activity)
fun hideKeyBoard() = KeyboardUtils.hideSoftInput(Toolkit.getTopActivityOrApp() as Activity)

fun getHtmlLayout(content: String, textColor: String, backgroundColor: String): String = "<!DOCTYPE html> <html> <head> <link rel=\"stylesheet\" href=\"style.css\"> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> </head> <body style='color:$textColor background-color:$backgroundColor'>$content</body></html>"

fun playFromAsset(path: String) {
	try {
		val descriptor = Toolkit.getTopActivityOrApp().assets.openFd(path)
		val mediaPlayer = android.media.MediaPlayer()
		mediaPlayer.setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
		mediaPlayer.prepare()
		mediaPlayer.start()
		descriptor.close()
	} catch (e: IOException) {
		e.printStackTrace()
	}
}

fun isFileExist(filePath: String): Boolean = File(filePath).exists()

fun Context.openFile(fileName: String, filePath: String, type: String) = if (Build.VERSION.SDK_INT >= 26) startActivity(Intent(Intent.ACTION_VIEW).setDataAndType(FileProvider.getUriForFile(this, applicationContext.packageName + ".my.package.name.provider", File(filePath, "$fileName.pdf")), type).addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION))
else this.startActivity(Intent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).setAction(Intent.ACTION_VIEW).setDataAndType(Uri.fromFile(File("$filePath$fileName")), type))

fun WebView.loadData(content: String, textColor: String = "black", backgroundColor: String = "white", mimeType: String = "text/html", encoding: String = "utf-8") = this.loadData(getHtmlLayout(content, textColor, backgroundColor), mimeType, encoding)

fun Context.hideKeyboard(view: View) {
	val inputMethodManager: InputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.hideKeyboardOnClick() = this.setOnClickListener { Toolkit.getTopActivityOrApp().hideKeyboard(this) }

fun permission(permission: String, permissionCallBack: PermissionCallBack) = PermissionUtils.permission(permission).callback(object : PermissionUtils.SimpleCallback {
	override fun onGranted() = permissionCallBack.onGranted()
	override fun onDenied() = permissionCallBack.onDenied()
}).request()

fun jsonObject(vararg map: Pair<String, Any>): JsonObject {
	val jsonObject = JsonObject()
	for (i in map) {
		if (i.second is Number) jsonObject.addProperty(i.first, i.second as Number)
		if (i.second is String) jsonObject.addProperty(i.first, i.second as String)
		if (i.second is Boolean) jsonObject.addProperty(i.first, i.second as Boolean)
	}
	return jsonObject
}

fun loadJSONFromAsset(): String? {
	val json: String
	val `is`: InputStream
	try {
		`is` = Toolkit.getTopActivityOrApp().assets.open("location.json")
		val size = `is`.available()
		val buffer = ByteArray(size)
		`is`.read(buffer)
		`is`.close()
		json = String(buffer, StandardCharsets.UTF_8)
	} catch (ex: IOException) {
		ex.printStackTrace()
		return null
	}
	try {
		@SuppressLint("SimpleDateFormat") val df = SimpleDateFormat("hh:mm")
		val d1 = df.parse("23:30")
		val c1 = GregorianCalendar.getInstance()
		c1.time = d1
	} catch (e: ParseException) {
		e.printStackTrace()
	}
	return json
}

fun duration(d: Int): String = if (d <= 60) "$d  ثانیه " else "${d / 60}  دقیقه "

fun copyText(text: CharSequence) {
	val cm = Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
	cm.setPrimaryClip(ClipData.newPlainText("text", text))
}

interface PermissionCallBack {
	fun onGranted()
	fun onDenied()
}