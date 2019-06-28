package com.developersian.view

import android.os.Bundle
import com.developersian.R
import com.developersian.base.BaseActivity
import com.developersian.util.extentions.startActivity

class SplashActivity : BaseActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_splash)
		
		startActivity(MainActivity::class.java,
		              Pair("1", "hello"),
		              Pair("2", 123),
		              Pair("3", true))
	}
}
