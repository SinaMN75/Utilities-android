package com.developersian.base

import android.content.Context
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
	lateinit var u: Util
	
	override fun onAttach(context: Context) {
		super.onAttach(context)
		u = Util()
	}
}