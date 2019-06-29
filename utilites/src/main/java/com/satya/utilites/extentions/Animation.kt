package com.satya.utilites.extentions

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.TextSwitcher
import android.widget.TextView
import com.satya.utilites.Utilities.Toolkit

fun View.animateAlpha(startDelay: Long, duration: Long, alpha: Float) = this.animate().setStartDelay(startDelay).setDuration(duration).alpha(alpha).start()

fun View.animateMove(startDelay: Long, duration: Long, x: Float, y: Float) = this.animate().setStartDelay(startDelay).setDuration(duration).translationX(x).translationY(y).start()

fun View.animateRotate(startDelay: Long, duration: Long, rotate: Float) = this.animate().setStartDelay(startDelay).setDuration(duration).rotation(rotate).start()

fun TextSwitcher.handleTextSwitcher(textSize: Float, textColor: Int) {
	this.inAnimation = animation(android.R.anim.slide_in_left)
	this.outAnimation = animation(android.R.anim.slide_out_right)
	this.setFactory {
		val textView = TextView(Toolkit.getTopActivityOrApp())
		textView.textSize = textSize
		textView.setTextColor(color(textColor))
		textView.gravity = Gravity.START
		textView.typeface = Typeface.createFromAsset(Toolkit.getTopActivityOrApp().assets, "FONT")
		textView
	}
}