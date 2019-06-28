package com.developersian.util.extentions

import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun visible(vararg view: View) {
	for (i in view) i.visibility = View.VISIBLE
}

fun invisible(vararg view: View) {
	for (i in view) i.visibility = View.INVISIBLE
}

fun gone(vararg view: View) {
	for (i in view) i.visibility = View.GONE
}

fun picasso(url: String?, imageView: ImageView, placeholder: Int) {
	if (url != "") Picasso.get().load(url).placeholder(drawable(placeholder)).into(imageView)
	else setDrawable(imageView, placeholder)
}

fun picasso(url: String?, imageView: ImageView) {
	if (url != "") Picasso.get().load(url).into(imageView)
}