package com.satya.utilites.extentions

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.satya.utilites.Utilities.Toolkit

fun startActivity(clz: Class<out Activity>, vararg extra: Pair<String, Any>, enterAnim: Int = android.R.anim.slide_in_left, exitAnim: Int = android.R.anim.slide_out_right) {
	val context = Toolkit.getTopActivityOrApp()
	val bundle = Bundle()
	for (i in extra) putBundle(i, bundle)
	startActivity(context, bundle, context.packageName, clz.name, getOptionsBundle(context, enterAnim, exitAnim))
}

fun Activity.intentString(key: String): String? = this.intent.getStringExtra(key)
fun Activity.intentInt(key: String): Int? = this.intent.getIntExtra(key, -1)
fun Activity.intentFloat(key: String): Float? = this.intent.getFloatExtra(key, -1F)
fun Activity.intentDouble(key: String): Double? = this.intent.getDoubleExtra(key, -1.0)
fun Activity.intentBoolean(key: String): Boolean? = this.intent.getBooleanExtra(key, false)

fun Fragment.navigate(fragment: Fragment, navDestination: Int, vararg extra: Pair<String, Any> = emptyArray()) {
	val bundle = Bundle()
	for (i in extra) putBundle(i, bundle)
	fragment.findNavController().navigate(navDestination, bundle)
}

fun Fragment.argumentString(key: String): String? = this.arguments?.getString(key)
fun Fragment.argumentInt(key: String): Int? = this.arguments?.getInt(key)
fun Fragment.argumentFloat(key: String): Float? = this.arguments?.getFloat(key)
fun Fragment.argumentDouble(key: String): Double? = this.arguments?.getDouble(key)
fun Fragment.argumentBoolean(key: String): Boolean? = this.arguments?.getBoolean(key)

private fun putBundle(i: Pair<String, Any>, bundle: Bundle) {
	if (i.second is String) bundle.putString(i.first, i.second.toString())
	if (i.second is Int) bundle.putInt(i.first, i.second.toString().toInt())
	if (i.second is Float) bundle.putFloat(i.first, i.second.toString().toFloat())
	if (i.second is Double) bundle.putDouble(i.first, i.second.toString().toDouble())
	if (i.second is Boolean) bundle.putBoolean(i.first, i.second.toString().toBoolean())
}

private fun getOptionsBundle(context: Context, enterAnim: Int, exitAnim: Int): Bundle? = ActivityOptionsCompat.makeCustomAnimation(context, enterAnim, exitAnim).toBundle()

private fun startActivity(context: Context, extras: Bundle?, pkg: String, cls: String, options: Bundle?) {
	val intent = Intent(Intent.ACTION_VIEW)
	if (extras != null) intent.putExtras(extras)
	intent.component = ComponentName(pkg, cls)
	if (context !is Activity) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
	if (options != null) context.startActivity(intent, options)
	else context.startActivity(intent)
}