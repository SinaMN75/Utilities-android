package com.developersian.util.extentions

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developersian.base.App

fun linearLayoutForRecyclerView(vararg recyclerViews: RecyclerView, nested: Boolean = true, hasFixedSize: Boolean = true) {
	for (i in recyclerViews) {
		i.layoutManager = LinearLayoutManager(App.context)
		i.isNestedScrollingEnabled = nested
		i.setHasFixedSize(hasFixedSize)
	}
}

fun linearLayoutForRecyclerViewHorizontal(vararg recyclerViews: RecyclerView, nested: Boolean = true, hasFixedSize: Boolean = true) {
	for (i in recyclerViews) {
		i.layoutManager = LinearLayoutManager(App.context, LinearLayoutManager.HORIZONTAL, false)
		i.isNestedScrollingEnabled = nested
		i.setHasFixedSize(hasFixedSize)
	}
}

fun gridLayoutForRecyclerView(vararg recyclerViews: RecyclerView, spanCount: Int, nested: Boolean = true, hasFixedSize: Boolean = true) {
	for (i in recyclerViews) {
		i.layoutManager = GridLayoutManager(App.context, spanCount)
		i.isNestedScrollingEnabled = nested
		i.setHasFixedSize(hasFixedSize)
	}
}