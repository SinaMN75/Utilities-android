package com.developersian.util.extentions

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import androidx.core.content.FileProvider
import com.developersian.base.App
import com.google.gson.JsonObject
import java.io.File
import java.io.IOException
import java.util.*

class Foursome<out A, out B, out C, out D>(val first: A, val second: B, val third: C, val forth: D)

fun log(tag: String, message: String) = Log.i(tag, message)
fun log(tag: String, message: Int) = Log.i(tag, message.toString())
fun log(tag: String, message: Float) = Log.i(tag, message.toString())
fun log(tag: String, message: Double) = Log.i(tag, message.toString())
fun log(tag: String, message: JsonObject) = Log.i(tag, message.toString())

fun dpToPx(resources: Resources, dp: Float): Float = dp * resources.displayMetrics.density + 0.5f
fun spToPx(resources: Resources, sp: Float): Float = sp * resources.displayMetrics.scaledDensity

fun showKeyboard() = Objects.requireNonNull(App.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

fun getHtmlLayout(content: String, textColor: String, backgroundColor: String): String = "<!DOCTYPE html> <html> <head> <link rel=\"stylesheet\" href=\"style.css\"> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> </head> <body style='color:$textColor background-color:$backgroundColor'>$content</body></html>"

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

fun isFileExist(filePath: String): Boolean = File(filePath).exists()

fun openFile(context: Context, fileName: String, filePath: String, type: String) = if (Build.VERSION.SDK_INT >= 26) context.startActivity(Intent(Intent.ACTION_VIEW).setDataAndType(FileProvider.getUriForFile(context, context.applicationContext.packageName + ".my.package.name.provider", File(filePath, "$fileName.pdf")), type).addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION))
else context.startActivity(Intent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).setAction(Intent.ACTION_VIEW).setDataAndType(Uri.fromFile(File("$filePath$fileName")), type))


fun WebView.loadData(content: String, textColor: String = "black", backgroundColor: String = "white", mimeType: String = "text/html", encoding: String = "utf-8") = this.loadData(getHtmlLayout(content, textColor, backgroundColor), mimeType, encoding)

fun Context.hideKeyboard(view: View) {
	val inputMethodManager: InputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun View.hideKeyboardOnClick() = this.setOnClickListener { App.context.hideKeyboard(this) }

