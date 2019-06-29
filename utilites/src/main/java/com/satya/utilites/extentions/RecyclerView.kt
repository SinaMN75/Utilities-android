package com.satya.utilites.extentions

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.satya.utilites.Utilities.Toolkit

private fun setup(recyclerView: RecyclerView, nested: Boolean = true, hasFixedSize: Boolean = true) {
	recyclerView.isNestedScrollingEnabled = nested
	recyclerView.setHasFixedSize(hasFixedSize)
}

fun RecyclerView.linearLayoutManager(nested: Boolean = true, hasFixedSize: Boolean = true) {
	this.layoutManager = LinearLayoutManager(Toolkit.getTopActivityOrApp())
	setup(this, nested, hasFixedSize)
}

fun RecyclerView.horizontalLinearLayoutManager(nested: Boolean = true, hasFixedSize: Boolean = true) {
	this.layoutManager = LinearLayoutManager(Toolkit.getTopActivityOrApp(), LinearLayoutManager.HORIZONTAL, false)
	setup(this, nested, hasFixedSize)
}

fun RecyclerView.gridLayoutManager(spanCount: Int, nested: Boolean = true, hasFixedSize: Boolean = true) {
	this.layoutManager = GridLayoutManager(Toolkit.getTopActivityOrApp(), spanCount)
	setup(this, nested, hasFixedSize)
}