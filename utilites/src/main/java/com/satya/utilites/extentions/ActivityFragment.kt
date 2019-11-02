package com.satya.utilites.extentions

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment
import com.satya.utilites.utilities.Toolkit

fun getTopActivity(): Activity = Toolkit.getTopActivityOrApp() as Activity

fun startActivity(clz: Class<out Activity>, vararg extra: Pair<String, Any>, enterAnim: Int = android.R.anim.slide_in_left, exitAnim: Int = android.R.anim.slide_out_right) {
	val context = Toolkit.getTopActivityOrApp()
	val bundle = Bundle()
	for (i in extra) putBundle(i, bundle)
	startActivity(context, bundle, context.packageName, clz.name, getOptionsBundle(context, enterAnim, exitAnim))
}

fun Activity.startActivityAndFinishCurrent(clz: Class<out Activity>, vararg extra: Pair<String, Any>, enterAnim: Int = android.R.anim.slide_in_left, exitAnim: Int = android.R.anim.slide_out_right) {
	val context = Toolkit.getTopActivityOrApp()
	val bundle = Bundle()
	for (i in extra) putBundle(i, bundle)
	startActivity(context, bundle, context.packageName, clz.name, getOptionsBundle(context, enterAnim, exitAnim))
	this.finish()
}

fun Activity.intentString(key: String): String? = intent.getStringExtra(key)
fun Activity.intentInt(key: String): Int? = intent.getIntExtra(key, -1)
fun Activity.intentFloat(key: String): Float? = intent.getFloatExtra(key, -1F)
fun Activity.intentDouble(key: String): Double? = intent.getDoubleExtra(key, -1.0)
fun Activity.intentBoolean(key: String): Boolean? = intent.getBooleanExtra(key, false)

fun startActivityForImage(requestCode: Int) = getTopActivity().startActivityForResult(Intent.createChooser(Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT), "Select Picture"), requestCode)

fun startActivityForVideo(requestCode: Int) = getTopActivity().startActivityForResult(Intent.createChooser(Intent().setType("video/*").setAction(Intent.ACTION_GET_CONTENT), "Select Video"), requestCode)

fun startActivity(clz: Class<out Activity>, options: Bundle, vararg extra: Pair<String, Any>) {
	val context = Toolkit.getTopActivityOrApp()
	val bundle = Bundle()
	for (i in extra) putBundle(i, bundle)
	startActivity(context, bundle, context.packageName, clz.name, options)
}

fun Fragment.navigate(navDestination: Int, vararg extra: Pair<String, Any> = emptyArray()) {
	val bundle = Bundle()
	for (i in extra) putBundle(i, bundle)
	NavHostFragment.findNavController(this).navigate(navDestination, bundle)
}

fun Fragment.transaction(layout: Int, fragment: Fragment) = this.fragmentManager!!.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(layout, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
fun transaction(layout: Int, fragment: Fragment, fragmentManager: FragmentManager) = fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(layout, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()

fun transactionAddToBackStack(layout: Int, fragment: Fragment, fragmentManager: FragmentManager) = fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(layout, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack("").commit()
fun Fragment.transactionAddToBackStack(layout: Int, fragment: Fragment) = this.fragmentManager!!.beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(layout, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack("").commit()

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