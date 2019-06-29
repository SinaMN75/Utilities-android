package com.developersian.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.developersian.R
import com.satya.utilites.Utilities.PermissionCallBack
import com.satya.utilites.constants.CAMERA
import com.satya.utilites.extentions.permission
import com.satya.utilites.extentions.toastShort

class MainActivity : AppCompatActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		permission(CAMERA, object : PermissionCallBack {
			override fun onGranted() {
				toastShort("granted")
			}
			
			override fun onDenied() {
				toastShort("denied")
			}
		})
	}
}
