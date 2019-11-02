package com.satya.utilites.extentions

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.satya.utilites.utilities.Toolkit
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

fun ImageView.setImageBitmap(bitmap: Bitmap) = this.setImageBitmap(bitmap)

fun Fragment.bindTv(id: Int) = view?.findViewById<TextView>(id)
fun Fragment.bindPb(id: Int) = view?.findViewById<ProgressBar>(id)
fun Fragment.bindBtn(id: Int) = view?.findViewById<Button>(id)
fun Fragment.bindIv(id: Int) = view?.findViewById<ImageView>(id)
fun Fragment.bindRv(id: Int) = view?.findViewById<RecyclerView>(id)
fun Fragment.bindEt(id: Int) = view?.findViewById<EditText>(id)
fun Fragment.bindVp(id: Int) = view?.findViewById<ViewPager>(id)
fun Fragment.bindView(id: Int) = view?.findViewById<View>(id)
fun Fragment.bindRb(id: Int) = view?.findViewById<RatingBar>(id)

fun Activity.bindTv(id: Int) = findViewById<TextView>(id)
fun Activity.bindPb(id: Int) = findViewById<ProgressBar>(id)
fun Activity.bindBtn(id: Int) = findViewById<Button>(id)
fun Activity.bindIv(id: Int) = findViewById<ImageView>(id)
fun Activity.bindRv(id: Int) = findViewById<RecyclerView>(id)
fun Activity.bindEt(id: Int) = findViewById<EditText>(id)
fun Activity.bindVp(id: Int) = findViewById<ViewPager>(id)
fun Activity.bindView(id: Int) = findViewById<View>(id)
fun Activity.bindRb(id: Int) = findViewById<RatingBar>(id)

fun RecyclerView.ViewHolder.bindTv(id: Int) = this.itemView.findViewById<TextView>(id)
fun RecyclerView.ViewHolder.bindBtn(id: Int) = this.itemView.findViewById<Button>(id)
fun RecyclerView.ViewHolder.bindIv(id: Int) = this.itemView.findViewById<ImageView>(id)
fun RecyclerView.ViewHolder.bindRv(id: Int) = this.itemView.findViewById<RecyclerView>(id)
fun RecyclerView.ViewHolder.bindEt(id: Int) = this.itemView.findViewById<EditText>(id)
fun RecyclerView.ViewHolder.bindVp(id: Int) = this.itemView.findViewById<ViewPager>(id)
fun RecyclerView.ViewHolder.bindView(id: Int) = this.itemView.findViewById<View>(id)
fun RecyclerView.ViewHolder.bindRb(id: Int) = this.itemView.findViewById<RatingBar>(id)
fun RecyclerView.ViewHolder.bindPb(id: Int) = this.itemView.findViewById<ProgressBar>(id)