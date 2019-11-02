package com.satya.utilites.extentions

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.satya.utilites.R
import com.satya.utilites.utilities.Toolkit

fun color(color: Int) = ContextCompat.getColor(Toolkit.getTopActivityOrApp(), color)

fun drawable(drawable: Int) = ContextCompat.getDrawable(Toolkit.getTopActivityOrApp(), drawable)!!

fun animation(animation: Int): Animation = AnimationUtils.loadAnimation(Toolkit.getTopActivityOrApp(), animation)

fun array(array: Int): Array<String> = Toolkit.getTopActivityOrApp().resources.getStringArray(array)

fun setDrawable(imageView: ImageView, drawable: Int) = imageView.setImageDrawable(ContextCompat.getDrawable(Toolkit.getTopActivityOrApp(), drawable))

fun persianMonth(): Array<String> = array(R.array.persianMonth)

fun grgMonth(): Array<String> = array(R.array.grgMonths)