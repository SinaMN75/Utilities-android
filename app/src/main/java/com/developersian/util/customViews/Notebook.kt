package com.developersian.util.customViews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.developersian.R

/**
 * a textView that has a blue line under its text.
 */
class Notebook @SuppressLint("ResourceAsColor")
constructor(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {
    private val rect: Rect = Rect()
	private val paint1: Paint = Paint()
	
	init {
		paint1.style = Paint.Style.FILL_AND_STROKE
        paint1.color = R.color.md_white_1000
    }

    @SuppressLint("ResourceAsColor")
    override fun onDraw(canvas: Canvas) {
        var count = 1
        if (lineCount > count) count = lineCount
        val r = rect
        val paint = paint1
        var baseline = getLineBounds(0, r)
        for (i in 0 until count) {
            canvas.drawLine(r.left.toFloat(), (baseline + 1).toFloat(), r.right.toFloat(), (baseline + 1).toFloat(), paint)
            baseline += lineHeight
            paint.color = Color.BLUE
        }
        super.onDraw(canvas)
    }
}