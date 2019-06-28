package com.developersian.util.extentions

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ImageUtils
import com.developersian.R
import com.developersian.base.App

fun color(color: Int) = ContextCompat.getColor(App.context, color)

fun drawable(drawable: Int) = ContextCompat.getDrawable(App.context, drawable)!!

fun bitmapFromDrawable(drawable: Int) = ImageUtils.getBitmap(drawable)!!

fun animation(animation: Int): Animation = AnimationUtils.loadAnimation(App.context, animation)

fun array(array: Int): Array<String> = App.context.resources.getStringArray(array)

fun setDrawable(imageView: ImageView, drawable: Int) = imageView.setImageDrawable(ContextCompat.getDrawable(App.context, drawable))

fun persianMonth(): Array<String> = array(R.array.persianMonth)

fun grgMonth(): Array<String> = array(R.array.grgMonths)