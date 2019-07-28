package com.satya.utilites.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import java.util.*
import java.util.concurrent.TimeUnit

class TimerTextView : AppCompatTextView {
	private var timer = Timer()
	private var endTime: Long = 0
	private var interval = DEFAULT_INTERVAL.toLong()
	private var isCanceled = false
	
	constructor(context: Context) : super(context)
	constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
	constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
	
	override fun onDetachedFromWindow() {
		super.onDetachedFromWindow()
		stopTimer()
	}
	
	override fun onVisibilityChanged(changedView: View, visibility: Int) {
		super.onVisibilityChanged(changedView, visibility)
		if (View.VISIBLE == visibility) startTimer()
		else stopTimer()
	}
	
	fun setInterval(interval: Long) {
		if (interval >= 0) {
			this.interval = interval
			stopTimer()
			startTimer()
		}
	}
	
	fun setEndTime(endTime: Long) {
		if (endTime >= 0) {
			this.endTime = endTime
			stopTimer()
			startTimer()
		}
	}
	
	private fun startTimer() {
		if (endTime == 0L) return
		if (isCanceled) {
			timer = Timer()
			isCanceled = false
		}
		timer.scheduleAtFixedRate(object : TimerTask() {
			override fun run() {
				if (null == handler) return
				handler.post { text = getDurationBreakdown(endTime - System.currentTimeMillis()) }
			}
		}, 0, interval)
	}
	
	private fun stopTimer() {
		timer.cancel()
		isCanceled = true
	}
	
	private fun getDurationBreakdown(diff: Long): String {
		var millis = diff
		if (millis < 0) return "00:00:00"
		val hours = TimeUnit.MILLISECONDS.toHours(millis)
		millis -= TimeUnit.HOURS.toMillis(hours)
		val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
		millis -= TimeUnit.MINUTES.toMillis(minutes)
		val seconds = TimeUnit.MILLISECONDS.toSeconds(millis)
		return String.format(Locale.ENGLISH, "%02d:%02d:%02d", hours, minutes, seconds)
	}
	
	companion object {
		private const val DEFAULT_INTERVAL = 1000
	}
}