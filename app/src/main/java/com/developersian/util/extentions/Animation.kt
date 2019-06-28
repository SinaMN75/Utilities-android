package com.developersian.util.extentions

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.TextSwitcher
import android.widget.TextView
import com.developersian.base.App

fun View.animateAlpha(startDelay: Long, duration: Long, alpha: Float) = this.animate().setStartDelay(startDelay).setDuration(duration).alpha(alpha).start()

fun View.animateMove(vararg views: View, startDelay: Long, duration: Long, x: Float, y: Float) = this.animate().setStartDelay(startDelay).setDuration(duration).translationX(x).translationY(y).start()

fun View.animateRotate(vararg views: View, startDelay: Long, duration: Long, rotate: Float) = this.animate().setStartDelay(startDelay).setDuration(duration).rotation(rotate).start()

fun TextSwitcher.handleTextSwitcher(textSize: Float, textColor: Int) {
	this.inAnimation = animation(android.R.anim.slide_in_left)
	this.outAnimation = animation(android.R.anim.slide_out_right)
	this.setFactory {
		val textView = TextView(App.context)
		textView.textSize = textSize
		textView.setTextColor(color(textColor))
		textView.gravity = Gravity.START
		textView.typeface = Typeface.createFromAsset(App.context.assets, "FONT")
		textView
	}
}