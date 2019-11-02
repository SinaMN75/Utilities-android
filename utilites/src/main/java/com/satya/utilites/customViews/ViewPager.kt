package com.satya.utilites.customViews

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.satya.utilites.extentions.picasso

class ViewPager(private val context: Context, private val imageUrls: ArrayList<String>) : PagerAdapter() {
	override fun getCount(): Int = imageUrls.size
	override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`
	override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) = container.removeView(`object` as View)
	override fun instantiateItem(container: ViewGroup, position: Int): Any {
		val imageView = ImageView(context)
		imageView.picasso(imageUrls[position])
		container.addView(imageView)
		return imageView
	}
}