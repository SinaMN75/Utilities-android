package com.satya.utilites.extentions

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.satya.utilites.Utilities.Toolkit

fun RecyclerView.linearLayoutManager(nested: Boolean = true, hasFixedSize: Boolean = true) {
	this.layoutManager = LinearLayoutManager(Toolkit.getTopActivityOrApp())
	this.isNestedScrollingEnabled = nested
	this.setHasFixedSize(hasFixedSize)
}

fun RecyclerView.horizontalLinearLayoutManager(nested: Boolean = true, hasFixedSize: Boolean = true) {
	this.layoutManager = LinearLayoutManager(Toolkit.getTopActivityOrApp(), LinearLayoutManager.HORIZONTAL, false)
	this.isNestedScrollingEnabled = nested
	this.setHasFixedSize(hasFixedSize)
}

fun RecyclerView.gridLayoutManager(spanCount: Int, nested: Boolean = true, hasFixedSize: Boolean = true) {
	this.layoutManager = GridLayoutManager(Toolkit.getTopActivityOrApp(), spanCount)
	this.isNestedScrollingEnabled = nested
	this.setHasFixedSize(hasFixedSize)
}