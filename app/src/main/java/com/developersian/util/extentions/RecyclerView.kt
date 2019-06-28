package com.developersian.util.extentions

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developersian.base.App

fun RecyclerView.linearLayoutManager(nested: Boolean = true, hasFixedSize: Boolean = true) {
	this.layoutManager = LinearLayoutManager(App.context)
	this.isNestedScrollingEnabled = nested
	this.setHasFixedSize(hasFixedSize)
}

fun RecyclerView.horizontalLinearLayoutManager(nested: Boolean = true, hasFixedSize: Boolean = true) {
	this.layoutManager = LinearLayoutManager(App.context, LinearLayoutManager.HORIZONTAL, false)
	this.isNestedScrollingEnabled = nested
	this.setHasFixedSize(hasFixedSize)
}

fun RecyclerView.gridLayoutManager(spanCount: Int, nested: Boolean = true, hasFixedSize: Boolean = true) {
	this.layoutManager = GridLayoutManager(App.context, spanCount)
	this.isNestedScrollingEnabled = nested
	this.setHasFixedSize(hasFixedSize)
}