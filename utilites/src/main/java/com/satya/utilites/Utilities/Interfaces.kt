package com.satya.utilites.Utilities

interface PermissionCallBack {
	fun onGranted()
	fun onDenied()
}

interface OnTextInputConfirmListener {
	fun onTextInputConfirmed(text: String)
}

interface OnItemsSelectedListener<T> {
	fun onItemsSelected(positions: List<Int>, items: List<T>)
}

interface OnItemSelectedListener<T> {
	fun onItemSelected(position: Int, item: T)
}