package com.developersian.util.extentions

import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun View.visible() {
	this.visibility = View.VISIBLE
}

fun View.invisible(vararg view: View) {
	this.visibility = View.INVISIBLE
}

fun View.gone(vararg view: View) {
	this.visibility = View.GONE
}

fun ImageView.picasso(url: String?, placeholder: Int) {
	if (url != "") Picasso.get().load(url).placeholder(drawable(placeholder)).into(this)
	else setDrawable(this, placeholder)
}

fun ImageView.picasso(url: String?) {
	if (url != "") Picasso.get().load(url).into(this)
}