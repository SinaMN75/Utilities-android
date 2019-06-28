package com.developersian.util.extentions

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.developersian.util.Utilities.Toolkit
import java.util.*

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

fun Fragment.navigate(navDestination: Int, vararg extra: String = arrayOf("")) {
	val bundle = Bundle()
	for (index in 0 until extra.size) bundle.putString(index.toString(), extra[index])
	findNavController().navigate(navDestination, bundle)
}

fun Fragment.argument(key: String): String = this.arguments!!.getString(key)!!

private fun getOptionsBundle(context: Context, enterAnim: Int, exitAnim: Int): Bundle? = ActivityOptionsCompat.makeCustomAnimation(context, enterAnim, exitAnim).toBundle()
private fun startActivity(context: Context, extras: Bundle?, pkg: String, cls: String, options: Bundle?) {
	val intent = Intent(Intent.ACTION_VIEW)
	if (extras != null) intent.putExtras(extras)
	intent.component = ComponentName(pkg, cls)
	if (context !is Activity) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
	if (options != null) context.startActivity(intent, options)
	else context.startActivity(intent)
}