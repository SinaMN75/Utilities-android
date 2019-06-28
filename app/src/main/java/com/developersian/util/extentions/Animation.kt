package com.developersian.util.extentions

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.TextSwitcher
import android.widget.TextView
import com.developersian.base.App

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