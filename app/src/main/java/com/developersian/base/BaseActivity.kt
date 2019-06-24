package com.developersian.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

open class BaseActivity : AppCompatActivity() {
    lateinit var u: Util

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        u = Util()
    }
}
