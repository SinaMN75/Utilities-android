package com.developersian.util.infiniteRecyclerView

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class InfiniteScrollProvider {
	private var recyclerView: RecyclerView? = null
	private var isLoading = false
	private var onLoadMoreListener: OnLoadMoreListener? = null
	private var lastVisibleItem: Int = 0
	private var totalItemCount: Int = 0
	private var previousItemCount = 0
	
	fun attach(recyclerView: RecyclerView, onLoadMoreListener: OnLoadMoreListener) {
		this.recyclerView = recyclerView
		this.onLoadMoreListener = onLoadMoreListener
		val layoutManager = recyclerView.layoutManager
		setInfiniteScrollGrid(layoutManager)
	}
	
	private fun setInfiniteScrollGrid(layoutManager: RecyclerView.LayoutManager?) {
		recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
			override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
				totalItemCount = layoutManager!!.itemCount
				if (previousItemCount > totalItemCount) previousItemCount = totalItemCount - THRESHOLD
				if (layoutManager is GridLayoutManager) {
					lastVisibleItem = layoutManager.findLastVisibleItemPosition()
				} else if (layoutManager is LinearLayoutManager) {
					lastVisibleItem = layoutManager.findLastVisibleItemPosition()
				} else if (layoutManager is StaggeredGridLayoutManager) {
					val staggeredGridLayoutManager = layoutManager as StaggeredGridLayoutManager?
					val spanCount = staggeredGridLayoutManager!!.spanCount
					val ids = IntArray(spanCount)
					staggeredGridLayoutManager.findLastVisibleItemPositions(ids)
					var max = ids[0]
					for (i in 1 until ids.size) if (ids[1] > max) max = ids[1]
					lastVisibleItem = max
				}
				if (totalItemCount > THRESHOLD) {
					if (previousItemCount <= totalItemCount && isLoading) {
						isLoading = false
						Log.i("InfiniteScroll", "Data fetched")
					}
					if (!isLoading && lastVisibleItem > totalItemCount - THRESHOLD && totalItemCount > previousItemCount) {
						if (onLoadMoreListener != null) onLoadMoreListener!!.onLoadMore()
						Log.i("InfiniteScroll", "End Of List")
						isLoading = true
						previousItemCount = totalItemCount
					}
				}
				super.onScrolled(recyclerView, dx, dy)
			}
		})
	}
	
	interface OnLoadMoreListener {
		fun onLoadMore()
	}
	
	companion object {
		private const val THRESHOLD = 3
	}
}