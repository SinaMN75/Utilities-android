package com.satya.utilites.extentions

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.TextSwitcher
import android.widget.TextView
import com.satya.utilites.utilities.Toolkit

fun View.animateAlpha(startDelay: Long, duration: Long, alpha: Float) = animate().setStartDelay(startDelay).setDuration(duration).alpha(alpha).start()

fun View.animateMove(startDelay: Long, duration: Long, x: Float, y: Float) = animate().setStartDelay(startDelay).setDuration(duration).translationX(x).translationY(y).start()

fun View.animateRotate(startDelay: Long, duration: Long, rotate: Float) = animate().setStartDelay(startDelay).setDuration(duration).rotation(rotate).start()

fun View.animateVisible() {
	visibility = View.VISIBLE
	animateAlpha(0, 1, 1f)
}

fun TextSwitcher.handleTextSwitcher(textSize: Float, textColor: Int) {
	inAnimation = animation(android.R.anim.slide_in_left)
	outAnimation = animation(android.R.anim.slide_out_right)
	setFactory {
		val textView = TextView(Toolkit.getTopActivityOrApp())
		textView.textSize = textSize
		textView.setTextColor(color(textColor))
		textView.gravity = Gravity.START
		textView.typeface = Typeface.createFromAsset(Toolkit.getTopActivityOrApp().assets, "FONT")
		textView
	}
}