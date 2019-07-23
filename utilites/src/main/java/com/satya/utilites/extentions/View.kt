package com.satya.utilites.extentions

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.satya.utilites.Utilities.Toolkit
import com.squareup.picasso.Picasso
import java.io.IOException


fun View.visible() {
	this.visibility = View.VISIBLE
}

fun View.invisible() {
	this.visibility = View.INVISIBLE
}

fun View.gone() {
	this.visibility = View.GONE
}

fun ImageView.picasso(url: String?, placeholder: Int) {
	if (url != "") Picasso.get().load(url).placeholder(drawable(placeholder)).into(this)
	else setDrawable(this, placeholder)
}

fun ImageView.picasso(url: String?) {
	if (url != "") Picasso.get().load(url).into(this)
}

fun ImageView.asset(path: String) {
	try {
		val image = Toolkit.getTopActivityOrApp().assets.open(path)
		this.setImageDrawable(Drawable.createFromStream(image, null))
		image.close()
	} catch (ex: IOException) {
		ex.printStackTrace()
	}
}