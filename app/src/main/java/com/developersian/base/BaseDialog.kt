package com.developersian.base

import android.content.Context
import androidx.fragment.app.DialogFragment

open class BaseDialog : DialogFragment() {
	lateinit var u: Util
	
	override fun onAttach(context: Context) {
		super.onAttach(context)
		u = Util()
	}
}